package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.ShowSeats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatsRepository extends MongoRepository<ShowSeats, String> {

}
