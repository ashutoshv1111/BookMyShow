package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.Show;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface ShowRepository extends MongoRepository<Show, String> {


    @Query("{ 'movieId': ?0 }")
    List<Show> getAllShowsOfMovie(String movieId);

    List<Show> findByMovieId(String movieId);

    boolean existsByTheatreIdAndDateAndStartTimeAndEndTime(String theatreId, Date date, LocalTime startTime, LocalTime endTime);
}
