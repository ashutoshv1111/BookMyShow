package com.social.bookmyshow.service;

import com.social.bookmyshow.Transformers.TheatreTransformer;
import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.model.Seat;
import com.social.bookmyshow.model.SeatType;
import com.social.bookmyshow.model.Theatre;
import com.social.bookmyshow.payload.SeatDTO;
import com.social.bookmyshow.payload.TheatreDTO;
import com.social.bookmyshow.repositories.SeatRepository;
import com.social.bookmyshow.repositories.TheatreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreServiceImplementation implements TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Theatre getTheatre(String theatreId) {
        return theatreRepository.findById(theatreId)
                .map(theatre -> modelMapper.map(theatre, Theatre.class))
                .orElseThrow(() -> new APIException("There is no theatre"));
    }

    @Override
    public List<TheatreDTO> getTheatres() {
        List<Theatre> theatres = theatreRepository.findAll();
        if (theatres.isEmpty()) {
            throw new APIException("There are no theatres");
        }
        return theatres.stream()
                .map(theatre -> modelMapper.map(theatre, TheatreDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TheatreDTO addTheatre(String theatreId, TheatreDTO theatreDTO) {
        if (theatreRepository.findById(theatreId).isPresent()) {
            throw new APIException("Theatre already exists");
        }
        Theatre theatre = TheatreTransformer.toTheatreTransformer(theatreDTO);
        theatreRepository.save(theatre);
        return modelMapper.map(theatre, TheatreDTO.class);
    }

    @Override
    public TheatreDTO addTheaterSeat(SeatDTO seatDTO) {
            Theatre theatre = theatreRepository.findById(seatDTO.getTheatreId())
                    .orElseThrow(() -> new APIException("There is no theatre"));

            List<Seat> seatList = new ArrayList<>();

            if (theatre.getSeatIds() != null && !theatre.getSeatIds().isEmpty()) {
                seatList = seatRepository.findAllById(theatre.getSeatIds());
            }

            seatList.addAll(createSeats(seatDTO.getSILVERSeatPrice(), SeatType.SILVER, theatre.getTheatreId()));
            seatList.addAll(createSeats(seatDTO.getGOLSeatPrice(), SeatType.GOLD, theatre.getTheatreId()));

            seatRepository.saveAll(seatList);

            List<String> seatIds = seatList.stream()
                    .map(Seat::getSeatId)
                    .collect(Collectors.toList());

            theatre.setSeatIds(seatIds);

            theatreRepository.save(theatre);

            return modelMapper.map(theatre, TheatreDTO.class);
        }

        private List<Seat> createSeats(int noOfSeats, SeatType seatType, String theatreId) {
            List<Seat> seats = new ArrayList<>();
            long seatNo = 1;
            for (int i = 0; i < noOfSeats; i++) {
                seats.add(new Seat(seatNo, seatType, theatreId));
                seatNo++;
            }
            return seats;
        }

    }

