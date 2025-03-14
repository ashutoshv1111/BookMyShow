package com.social.bookmyshow.service;

import com.social.bookmyshow.Transformers.TicketTransformer;
import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.model.*;
import com.social.bookmyshow.payload.TicketDTO;
import com.social.bookmyshow.payload.TicketResponseDTO;
import com.social.bookmyshow.repositories.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImplementation implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ShowSeatsRepository showSeatsRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheatreRepository theatreRepository;

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

//        System.out.println("Requested Seat IDs: " + seatIdsAsString);

        List<ShowSeats> showSeats = showSeatsRepository.findAllById(seatIdsAsString);

//        System.out.println("Fetched Seats from DB: " + showSeats);

        if (showSeats.isEmpty()) {
            throw new APIException("Selected seats not found in the database.");
        }

        boolean isSeatAvailable = isSeatAvailable(showSeats, seatIdsAsString);
        if (!isSeatAvailable) {
            throw new APIException("Selected seats are already booked.");
        }

        int totalAmount = getPriceAndAssignSeats(showSeats, seatIdsAsString);
        System.out.println("Total Amount Calculated: " + totalAmount);

        if (totalAmount == 0) {
            throw new APIException("Price calculation failed, seats may not exist.");
        }

        String seats = listToString(seatIdsAsString);

        Ticket ticket = new Ticket();
        ticket.setPrice(totalAmount);
        ticket.setBookedSeats(seats);
        ticket.setUserId(appUser.getUserId());
        ticket.setShowId(show.getShowId());

        ticket = ticketRepository.save(ticket);

        if (appUser.getTicketIds() == null) {
            appUser.setTicketIds(new ArrayList<>());
        }
        appUser.getTicketIds().add(ticket.getTicketId());

        if (show.getTicketIds() == null) {
            show.setTicketIds(new ArrayList<>());
        }
        show.getTicketIds().add(ticket.getTicketId());

        userRepository.save(appUser);
        showRepository.save(show);
        showSeatsRepository.saveAll(showSeats);

        return TicketTransformer.toTicketResponseDTO(ticket,show,movie,theatre,seats,totalAmount);
    }

    private Boolean isSeatAvailable(List<ShowSeats> showSeatList, List<String> seats) {
        for (ShowSeats showSeat : showSeatList) {
            if (seats.contains(showSeat.getShowSeatsId().toString())) {
                if (showSeat.isBooked()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Integer getPriceAndAssignSeats(List<ShowSeats> showSeatList, List<String> requestSeats) {
        int totalAmount = 0;
        for (ShowSeats showSeat : showSeatList) {
            if (requestSeats.contains(showSeat.getShowSeatsId().toString())) {
                totalAmount += showSeat.getPrice();
                showSeat.setBooked(true);
                System.out.println("Booking seat ID: " + showSeat.getShowSeatsId() + " | Price: " + showSeat.getPrice());
            }
        }
        System.out.println("Total Amount Calculated: " + totalAmount);
        return totalAmount;
    }

    private String listToString(List<String> requestSeats) {
        return String.join(",", requestSeats);
    }

    @Override
    public TicketDTO getTicket(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new APIException("Ticket Not Found"));
        return modelMapper.map(ticket, TicketDTO.class);
    }
}
