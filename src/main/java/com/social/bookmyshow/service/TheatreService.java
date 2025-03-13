package com.social.bookmyshow.service;

import com.social.bookmyshow.model.Theatre;
import com.social.bookmyshow.payload.SeatDTO;
import com.social.bookmyshow.payload.TheatreDTO;

import java.util.List;

public interface TheatreService {
    Theatre getTheatre(String theatreId);

    List<TheatreDTO> getTheatres();

    TheatreDTO addTheatre(String theatreId, TheatreDTO theatreDTO);

    public TheatreDTO addTheaterSeat(SeatDTO seatDTO);
}
