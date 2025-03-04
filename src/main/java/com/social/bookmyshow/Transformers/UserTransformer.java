package com.social.bookmyshow.Transformers;

import com.social.bookmyshow.model.AppUser;
import com.social.bookmyshow.payload.UserDTO;
import org.springframework.security.core.userdetails.User;

public class UserTransformer {
    public static AppUser userDTOTransformer(UserDTO userDTO) {
        AppUser user = AppUser.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .ticketIds(userDTO.getTicketIds())
                .build();
        return user;
    }
}
