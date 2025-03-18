package com.social.bookmyshow.service;

import com.social.bookmyshow.payload.MovieDTO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getMovies();

    MovieDTO getMovieById(String movieId);

    MovieDTO createMovie(MovieDTO movieDTO);

    MovieDTO updateMovie(String movieId, MovieDTO movieDTO);

    MovieDTO deleteMovie(String movieId);

    @Cacheable(value = "movies", key = "#query")
    List<MovieDTO> searchMovies(String query);

    Long totalCollection(String movieId);
}
