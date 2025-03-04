package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.Theatre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends MongoRepository<Theatre, String> {
}
