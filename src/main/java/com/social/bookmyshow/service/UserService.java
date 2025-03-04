package com.social.bookmyshow.service;

import com.social.bookmyshow.payload.UserDTO;
import jakarta.validation.Valid;

public interface UserService {
    public UserDTO updateUser(@Valid String userId, UserDTO userDTO);

    UserDTO getUser(String userId);
}
