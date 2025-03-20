package com.social.bookmyshow.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.model.Movie;
import com.social.bookmyshow.model.Show;
import com.social.bookmyshow.model.Ticket;
import com.social.bookmyshow.payload.MovieDTO;
import com.social.bookmyshow.repositories.MovieRepository;
import com.social.bookmyshow.repositories.ShowRepository;
import com.social.bookmyshow.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImplementation implements MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    private final ElasticsearchClient elasticsearchClient;

    @Override
    @Cacheable(value="movies",key="'allMovies'")
    public List<MovieDTO> searchMoviesByElasticSearch(String query) throws IOException {
        SearchResponse<Movie> response = elasticsearchClient.search(s -> s
                        .index("movies")
                        .query(q -> q
                                .wildcard(w -> w
                                        .field("title")
                                        .value("*" + query + "*")
                                )
                        ),
                Movie.class);

        List<Movie>movies= response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value="movies",key="'allMovies'")
    public List<MovieDTO> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOS = movies.stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
        return movieDTOS;
    }
    @Override
    @Cacheable(value = "movies", key = "#movieId")
    public MovieDTO getMovieById(String movieId) {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new APIException("Movie not found");
        }
        return modelMapper.map(movieRepository.findById(movieId).get(), MovieDTO.class);
    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public MovieDTO createMovie(MovieDTO movieDTO) {
        if(movieRepository.findById(movieDTO.getMovieId()).isPresent()) {
            throw new APIException("Movie already exists");
        }
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        return modelMapper.map(movieRepository.save(movie), MovieDTO.class);
    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public MovieDTO updateMovie(String movieId, MovieDTO movieDTO) {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new APIException("Movie not found");
        }
        Movie movie = movieRepository.findById(movieId).get();
        Movie updatedMovie = modelMapper.map(movieDTO, Movie.class);
        return modelMapper.map(movieRepository.save(updatedMovie), MovieDTO.class);
    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public MovieDTO deleteMovie(String movieId) {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new APIException("Movie not found");
        }
        Movie movie = movieRepository.findById(movieId).get();
        movieRepository.delete(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }
    @Cacheable(value = "movies", key = "#query")
    @Override
    public List<MovieDTO> searchMovies(String query) {
        List<Movie> movies = movieRepository.searchMovies(query);
        if (movies.isEmpty()) {
            throw new APIException("No movies found matching the query: " + query);
        }
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
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
