package com.social.bookmyshow.service;

import com.social.bookmyshow.payload.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getMovies();

    MovieDTO getMovieById(String movieId);

    MovieDTO createMovie(MovieDTO movieDTO);

    MovieDTO updateMovie(String movieId, MovieDTO movieDTO);

    MovieDTO deleteMovie(String movieId);

    Long totalCollection(String movieId);
}
