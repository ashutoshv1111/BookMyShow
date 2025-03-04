package com.social.bookmyshow.controllers;

import com.social.bookmyshow.model.AppUser;
import com.social.bookmyshow.payload.UserDTO;
import com.social.bookmyshow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId){
        UserDTO updatedUserDTO=userService.getUser(userId);
        return new ResponseEntity<>(updatedUserDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId,@Valid @RequestBody UserDTO userDTO){
        UserDTO updatedUserDTO=userService.updateUser(userId,userDTO);
    return new ResponseEntity<>(updatedUserDTO,HttpStatus.OK);
    }
}
