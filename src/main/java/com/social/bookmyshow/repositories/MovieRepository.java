package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    @Query("{ '$or': [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +
            "{ 'actors': { $regex: ?0, $options: 'i' } }, " +
            "{ 'description': { $regex: ?0, $options: 'i' } } " +
            "] }")
    List<Movie> searchMovies(String query);
}
