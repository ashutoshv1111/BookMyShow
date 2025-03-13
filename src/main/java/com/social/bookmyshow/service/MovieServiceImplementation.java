package com.social.bookmyshow.service;

import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.model.Movie;
import com.social.bookmyshow.model.Show;
import com.social.bookmyshow.model.Ticket;
import com.social.bookmyshow.payload.MovieDTO;
import com.social.bookmyshow.repositories.MovieRepository;
import com.social.bookmyshow.repositories.ShowRepository;
import com.social.bookmyshow.repositories.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImplementation implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MovieDTO> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOS = movies.stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
        return movieDTOS;
    }

    @Override
    public MovieDTO getMovieById(String movieId) {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new APIException("Movie not found");
        }
        return modelMapper.map(movieRepository.findById(movieId).get(), MovieDTO.class);
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        if(movieRepository.findById(movieDTO.getMovieId()).isPresent()) {
            throw new APIException("Movie already exists");
        }
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        return modelMapper.map(movieRepository.save(movie), MovieDTO.class);
    }

    @Override
    public MovieDTO updateMovie(String movieId, MovieDTO movieDTO) {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new APIException("Movie not found");
        }
        Movie movie = movieRepository.findById(movieId).get();
        Movie updatedMovie = modelMapper.map(movieDTO, Movie.class);
        return modelMapper.map(movieRepository.save(updatedMovie), MovieDTO.class);
    }

    @Override
    public MovieDTO deleteMovie(String movieId) {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new APIException("Movie not found");
        }
        Movie movie = movieRepository.findById(movieId).get();
        movieRepository.delete(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    @Override
    public Long totalCollection(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new APIException("Movie not found"));

        List<Show> showListOfMovie = showRepository.getAllShowsOfMovie(movieId);
        long amount = 0;

        for (Show show : showListOfMovie) {
            List<String> ticketIds = show.getTicketIds();
            if (ticketIds == null || ticketIds.isEmpty()) {
                continue;
            }
            for (String ticketId : ticketIds) {
                Ticket ticket = ticketRepository.findById(ticketId)
                        .orElseThrow(() -> new APIException("One or More Ticket not found"));
                amount += ticket.getPrice();
            }
        }
        return amount;
    }
}
