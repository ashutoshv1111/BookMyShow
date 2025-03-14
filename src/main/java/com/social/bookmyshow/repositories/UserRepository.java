package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<AppUser, String> {
    AppUser findByEmail(String emailId);;

    boolean existsByEmail(String email);

}
