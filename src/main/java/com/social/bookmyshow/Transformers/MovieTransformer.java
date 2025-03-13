package com.social.bookmyshow.Transformers;

import com.social.bookmyshow.model.Movie;
import com.social.bookmyshow.payload.MovieDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MovieTransformer {
    public Movie toMovieTransformer(MovieDTO movieDTO){
        Movie movie=Movie.builder()
                .title(movieDTO.getTitle())
                .genre(movieDTO.getGenre())
                .actors(movieDTO.getActors())
                .duration(movieDTO.getDuration())
                .releaseDate(movieDTO.getReleaseDate())
                .description(movieDTO.getDescription())
                .rating(movieDTO.getRating())
                .build();
        return movie;
    }
    public MovieDTO toMovieDTOTransformer(Movie movie){
        return MovieDTO.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .actors(movie.getActors())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .build();

    }
}
