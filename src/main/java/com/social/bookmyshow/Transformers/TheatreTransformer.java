package com.social.bookmyshow.Transformers;

import com.social.bookmyshow.model.Theatre;
import com.social.bookmyshow.payload.TheatreDTO;

public class TheatreTransformer {
    public static Theatre toTheatreTransformer(TheatreDTO theatreDTO) {
        Theatre newTheatre = Theatre.builder()
                .address(theatreDTO.getAddress())
                .theatreId(theatreDTO.getTheatreId())
                .theatreName(theatreDTO.getTheatreName())
                .build();
        return newTheatre;
    }
    public static TheatreDTO toTheatreDTOTransformer(Theatre theatre) {
        TheatreDTO newTheatreDTO = TheatreDTO.builder()
                .address(theatre.getAddress())
                .theatreId(theatre.getTheatreId())
                .theatreName(theatre.getTheatreName())
                .build();
        return newTheatreDTO;
    }
}
