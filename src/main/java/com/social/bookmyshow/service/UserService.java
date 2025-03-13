package com.social.bookmyshow.service;

import com.social.bookmyshow.payload.TicketResponseDTO;
import com.social.bookmyshow.payload.UserDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    public UserDTO updateUser(@Valid String userId, UserDTO userDTO);

    UserDTO getUser(String userId);

    UserDTO deleteUser(String userId);

    List<TicketResponseDTO> getUserTickets(String userId);

    UserDTO addUser(UserDTO userDTO);
}
