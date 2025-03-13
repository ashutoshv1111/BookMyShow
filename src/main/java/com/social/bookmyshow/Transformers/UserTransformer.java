package com.social.bookmyshow.Transformers;

import com.social.bookmyshow.model.AppUser;
import com.social.bookmyshow.payload.UserDTO;

public class UserTransformer {
    public static AppUser toUserTransformer(UserDTO userDTO) {
       return AppUser.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }
    public static UserDTO toUserDTO(AppUser user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
