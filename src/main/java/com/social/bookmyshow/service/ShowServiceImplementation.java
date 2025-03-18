package com.social.bookmyshow.service;

import com.social.bookmyshow.Transformers.ShowTransformer;
import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.model.*;
import com.social.bookmyshow.payload.ShowDTO;
import com.social.bookmyshow.payload.ShowSeatsDTO;
import com.social.bookmyshow.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImplementation implements ShowService {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ShowSeatsRepository showSeatsRepository;

    @Override
    @Cacheable(value = "Show")
    public List<ShowDTO> getShow(String movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new APIException("Movie not found");
        }
        List<Show> shows = showRepository.findByMovieId(movieId);
        List<ShowDTO> showDTOs = shows.stream().map(show -> modelMapper.map(show, ShowDTO.class)).collect(Collectors.toList());
        return showDTOs;
    }

    @Override
    @CacheEvict(value="Show")
    public ShowDTO createShow(ShowDTO showDTO) {
        boolean showExists = showRepository.existsByTheatreIdAndDateAndStartTimeAndEndTime(
                showDTO.getTheatreId(), showDTO.getDate(), showDTO.getStartTime(), showDTO.getEndTime());

        if (showExists) {
            throw new APIException("A show with the same theatreId, movieId, date, startTime, and endTime already exists");
        }

        Show show = ShowTransformer.toShowTransformer(showDTO);

        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new APIException("Movie not found"));

        Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
                .orElseThrow(() -> new APIException("Theatre not found"));

        show.setMovieId(movie.getMovieId());
        show.setTheatreId(theatre.getTheatreId());

        showRepository.save(show);

        if (movie.getShowIds() == null) {
            movie.setShowIds(new ArrayList<>());
        }
        movie.getShowIds().add(show.getShowId());

        if (theatre.getShowIds() == null) {
            theatre.setShowIds(new ArrayList<>());
        }
        theatre.getShowIds().add(show.getShowId());

        theatreRepository.save(theatre);
        movieRepository.save(movie);

        return ShowTransformer.toShowDTOTransformer(show);
    }


    @Override
    @CacheEvict(value="Show")
    public ShowDTO associateShowSeats(ShowSeatsDTO showSeatsDTO) {
        Show show = showRepository.findById(showSeatsDTO.getShowId())
                .orElseThrow(() -> new APIException("Show not found"));
        if (show.getShowId() == null) {
            throw new APIException("Show ID cannot be null while associating seats.");
        }

        String theatreId = show.getTheatreId();
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new APIException("Theatre not found"));

        List<String> seatIds = theatre.getSeatIds();
        if (seatIds == null || seatIds.isEmpty()) {
            throw new APIException("No seats found for this theatre.");
        }

        List<Seat> seats = seatRepository.findAllById(seatIds);

        List<String> showSeatIds = show.getShowSeatIds();
        if (showSeatIds == null) {
            showSeatIds = new ArrayList<>();
        }

        List<ShowSeats> showSeats = new ArrayList<>();

        for (Seat seat : seats) {
            ShowSeats showSeat = new ShowSeats();
            showSeat.setSeatNumber(seat.getSeatNumber());
            showSeat.setSeatType(seat.getSeatType());
            showSeat.setShowId(show.getShowId());
            showSeat.setBooked(Boolean.FALSE);

            if (showSeat.getSeatType().equals(SeatType.SILVER)) {
                showSeat.setPrice(showSeatsDTO.getSILVERSeatPrice());
            } else {
                showSeat.setPrice(showSeatsDTO.getGOLSeatPrice());
            }

            showSeats.add(showSeat);
        }

        showSeatsRepository.saveAll(showSeats);

        List<String> newShowSeatIds = showSeats.stream()
                .map(ShowSeats::getShowSeatsId)
                .collect(Collectors.toList());

        showSeatIds.addAll(newShowSeatIds);
        show.setShowSeatIds(showSeatIds);

        showRepository.save(show);
        return ShowTransformer.toShowDTOTransformer(show);
    }
}