package com.social.bookmyshow.service;

import com.social.bookmyshow.Transformers.MovieTransformer;
import com.social.bookmyshow.Transformers.UserTransformer;
import com.social.bookmyshow.exceptionHandling.ResourceNotFoundException;
import com.social.bookmyshow.model.AppUser;
import com.social.bookmyshow.payload.UserDTO;
import com.social.bookmyshow.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
     AppUser user=userRepository.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", userId));
     AppUser newUser= UserTransformer.userDTOTransformer(userDTO);
     userRepository.save(newUser);
     return modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String userId) {
        return null;
    }
}
