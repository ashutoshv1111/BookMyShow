package com.social.bookmyshow.Transformers;

import com.social.bookmyshow.model.Show;
import com.social.bookmyshow.payload.ShowDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ShowTransformer {
    public static Show toShowTransformer(ShowDTO showDTO) {
        Show transformedShow = Show.builder()
                .movieId(showDTO.getMovieId())
                .theatreId(showDTO.getTheatreId())
                .startTime(showDTO.getStartTime())
                .endTime(showDTO.getEndTime())
                .date(showDTO.getDate())
                .build();
        return transformedShow;
    }

    public static ShowDTO toShowDTOTransformer(Show show) {
        ShowDTO showDTO = ShowDTO.builder()
                .movieId(show.getMovieId())
                .date(show.getDate())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .theatreId(show.getTheatreId())
                .build();
        return showDTO;
    }
}








