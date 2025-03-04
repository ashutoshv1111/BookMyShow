package com.social.bookmyshow.Transformers;

import com.social.bookmyshow.model.Movie;
import com.social.bookmyshow.payload.MovieDTO;

public class MovieTransformer {
    public Movie movieDTOTransformer(MovieDTO movieDTO){
        Movie movie=Movie.builder()
                .title(movieDTO.getTitle())
                .actors(movieDTO.getActors())
                .genre(movieDTO.getGenre())
                .rating(movieDTO.getRating())
                .description(movieDTO.getDescription())
                .releaseDate(movieDTO.getReleaseDate())
                .duration(movieDTO.getDuration())
                .build();
        return movie;
    }
}
