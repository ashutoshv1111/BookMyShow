package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
