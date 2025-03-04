package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.Screen;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScreenRepository extends MongoRepository<Screen, String> {
}
