package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TicketRepository extends MongoRepository<Ticket,String> {
}
