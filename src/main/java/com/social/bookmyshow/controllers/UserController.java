package com.social.bookmyshow.controllers;


import com.social.bookmyshow.payload.TicketResponseDTO;
import com.social.bookmyshow.payload.UserDTO;
import com.social.bookmyshow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    

    @GetMapping("/{userId}")
//    @PreAuthorize("hasRole('UsER')" or #userId==authentication.principal.id)
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId){
        UserDTO updatedUserDTO=userService.getUser(userId);
        return new ResponseEntity<>(updatedUserDTO,HttpStatus.OK);
    }

    @PutMapping("/{userId}")
//    @PreAuthorize("hasRole('ADMIN')" or #userId==authentication.principal.id)
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId,@Valid @RequestBody UserDTO userDTO){
        UserDTO updatedUserDTO=userService.updateUser(userId,userDTO);
    return new ResponseEntity<>(updatedUserDTO,HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
//    @PreAuthorize("hasRole('ADMIN')" or #userId==authentication.principal.id)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String userId){
        UserDTO deletedUserDTO=userService.deleteUser(userId);
        return new ResponseEntity<>(deletedUserDTO,HttpStatus.OK);
    }
    @PostMapping("")
    //    @PreAuthorize("hasRole('ADMIN')" or #userId==authentication.principal.id)
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        UserDTO createdUserDTO=userService.addUser(userDTO);
        return new ResponseEntity<>(createdUserDTO,HttpStatus.OK);
    }
    @GetMapping("/{userId}/tickets")
    public ResponseEntity<List<TicketResponseDTO>>getUserTickets(@PathVariable String userId){
        List<TicketResponseDTO> ticketList=userService.getUserTickets(userId);
        return new ResponseEntity<>(ticketList,HttpStatus.OK);
    }
}
