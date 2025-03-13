package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SeatRepository extends MongoRepository<Seat, String> {

    List<Seat> getSeatsBySeatId(String seatId);
}
