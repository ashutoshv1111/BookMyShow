package com.social.bookmyshow.service;

import com.social.bookmyshow.payload.ShowDTO;
import com.social.bookmyshow.payload.ShowSeatsDTO;

import java.util.List;

public interface ShowService {
    List<ShowDTO> getShow(String movieId);

    ShowDTO createShow(ShowDTO showDTO);

    ShowDTO associateShowSeats(ShowSeatsDTO showSeatsDTO);
}
