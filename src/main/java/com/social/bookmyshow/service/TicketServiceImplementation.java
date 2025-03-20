package com.social.bookmyshow.service;

import com.social.bookmyshow.Transformers.TicketTransformer;
import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.model.*;
import com.social.bookmyshow.payload.TicketDTO;
import com.social.bookmyshow.payload.TicketResponseDTO;
import com.social.bookmyshow.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImplementation implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ModelMapper modelMapper;
    private final ShowSeatsRepository showSeatsRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final PaymentService paymentService;

    @Transactional
    @CacheEvict(value = "Ticket")
    @Override
    public TicketResponseDTO addBooking(TicketDTO ticketDTO) {
        Show show = showRepository.findById(ticketDTO.getShowId())
                .orElseThrow(() -> new APIException("Show not found"));
        AppUser appUser = userRepository.findById(ticketDTO.getUserId())
                .orElseThrow(() -> new APIException("User not found"));
        Movie movie = movieRepository.findById(show.getMovieId())
                .orElseThrow(() -> new APIException("Movie not found"));
        Theatre theatre = theatreRepository.findById(show.getTheatreId())
                .orElseThrow(() -> new APIException("Theatre not found"));

        List<String> seatIdsAsString = ticketDTO.getSeatIds().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        List<ShowSeats> showSeats = showSeatsRepository.findAllById(seatIdsAsString);

        if (showSeats.isEmpty()) {
            throw new APIException("Selected seats not found in the database.");
        }

        boolean isSeatAvailable = checkSeatAvailabilityParallel(showSeats, seatIdsAsString);
        if (!isSeatAvailable) {
            throw new APIException("Selected seats are already booked.");
        }

        int totalAmount = assignSeatsParallel(showSeats, seatIdsAsString);
        if (totalAmount == 0) {
            throw new APIException("Price calculation failed, seats may not exist.");
        }

        boolean paymentSuccess = paymentService.processPayment(appUser.getUserId(), totalAmount);
        if (!paymentSuccess) {
            throw new APIException("Payment failed. Booking canceled.");
        }

        String seats = listToString(seatIdsAsString);
        Ticket ticket = new Ticket();
        ticket.setPrice(totalAmount);
        ticket.setBookedSeats(seats);
        ticket.setUserId(appUser.getUserId());
        ticket.setShowId(show.getShowId());

        try {
            ticket = ticketRepository.save(ticket);
            synchronized (this) {
                if (appUser.getTicketIds() == null) {
                    appUser.setTicketIds(new ArrayList<>());
                }
                appUser.getTicketIds().add(ticket.getTicketId());

                if (show.getTicketIds() == null) {
                    show.setTicketIds(new ArrayList<>());
                }
                show.getTicketIds().add(ticket.getTicketId());
            }

            userRepository.save(appUser);
            showRepository.save(show);
            showSeatsRepository.saveAll(showSeats);
        } catch (OptimisticLockingFailureException e) {
            throw new APIException("Concurrency issue: Another transaction modified the record. Please retry.");
        }

        return TicketTransformer.toTicketResponseDTO(show, movie, theatre, seats, totalAmount);
    }

    private Boolean checkSeatAvailabilityParallel(List<ShowSeats> showSeatList, List<String> seats) {
        return showSeatList.parallelStream().noneMatch(showSeat ->
                seats.contains(showSeat.getShowSeatsId().toString()) && showSeat.isBooked());
    }

    private Integer assignSeatsParallel(List<ShowSeats> showSeatList, List<String> requestSeats) {
        return showSeatList.parallelStream()
                .filter(showSeat -> requestSeats.contains(showSeat.getShowSeatsId()))
                .mapToInt(showSeat -> {
                    showSeat.setBooked(true);
                    return showSeat.getPrice();
                }).sum();
    }

    private String listToString(List<String> requestSeats) {
        return String.join(",", requestSeats);
    }

    @Cacheable(value = "Ticket")
    @Override
    public TicketDTO getTicket(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new APIException("Ticket Not Found"));
        return modelMapper.map(ticket, TicketDTO.class);
    }
}