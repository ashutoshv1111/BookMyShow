package com.social.bookmyshow.controllers;

import com.social.bookmyshow.model.Seat;
import com.social.bookmyshow.payload.SeatDTO;
import com.social.bookmyshow.payload.TicketDTO;
import com.social.bookmyshow.payload.TicketResponseDTO;
import com.social.bookmyshow.service.TicketService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @PostMapping("/book")
    public ResponseEntity<TicketResponseDTO> addBooking(@RequestBody TicketDTO ticketDTO) {
        TicketResponseDTO ticket = ticketService.addBooking(ticketDTO);
        return new ResponseEntity<>(ticket,HttpStatus.CREATED);
    }

}
