package com.social.bookmyshow.controllers;

import com.social.bookmyshow.model.Movie;
import com.social.bookmyshow.payload.MovieDTO;
import com.social.bookmyshow.service.MovieService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> getMovies() {
        List<MovieDTO>movieDTOs=movieService.getMovies();
        return new ResponseEntity<>(movieDTOs,HttpStatus.OK);
    }
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable String movieId) {
        MovieDTO movieDTO=movieService.getMovieById(movieId);
        return new ResponseEntity<>(movieDTO,HttpStatus.OK);
    }
    @PutMapping("/movies/{movieId}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable String movieId, @RequestBody MovieDTO movieDTO) {
        MovieDTO updatedMovieDTO=movieService.updateMovie(movieId,movieDTO);
        return new ResponseEntity<>(updatedMovieDTO,HttpStatus.OK);
    }
    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO updatedMovieDTO=movieService.createMovie(movieDTO);
        return new ResponseEntity<>(updatedMovieDTO,HttpStatus.CREATED);
    }
    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<MovieDTO> deleteMovie(@PathVariable String movieId) {
        MovieDTO deletedMovieDTO=movieService.deleteMovie(movieId);
        return new ResponseEntity<>(deletedMovieDTO,HttpStatus.OK);
    }
    @GetMapping("/movies/totalCollection/{movieId}")
    public ResponseEntity<Long> totalCollection(@PathVariable String movieId) {
            Long result = movieService.totalCollection(movieId);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
