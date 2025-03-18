package com.social.bookmyshow.repositories;

import com.social.bookmyshow.model.AppUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<AppUser, String> {
    boolean existsByEmail(String email);
    AppUser findByUsername(@NotBlank String username);

    boolean existsByUsername(@NotBlank @Size(min = 3, max = 20) String username);
}
