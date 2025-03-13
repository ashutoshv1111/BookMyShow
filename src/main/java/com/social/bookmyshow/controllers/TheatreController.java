package com.social.bookmyshow.controllers;

import com.social.bookmyshow.model.Theatre;
import com.social.bookmyshow.payload.SeatDTO;
import com.social.bookmyshow.payload.TheatreDTO;
import com.social.bookmyshow.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;
    @GetMapping("/theatres/{theatreId}")
    public ResponseEntity<Theatre> getTheatre(@PathVariable String theatreId) {
        Theatre theatre = theatreService.getTheatre(theatreId);
        return new ResponseEntity<>(theatre, HttpStatus.OK);
    }
    @GetMapping("/theatres")
    public ResponseEntity<List<TheatreDTO>> getTheatres() {
        List<TheatreDTO> theatres = theatreService.getTheatres();
        return new ResponseEntity<>(theatres,HttpStatus.OK);
    }
    @PostMapping("/theatres/{theatreId}")
    public ResponseEntity<TheatreDTO> addTheatre(@PathVariable String theatreId,@RequestBody TheatreDTO theatreDTO) {
        TheatreDTO createdTheatreDTO =  theatreService.addTheatre(theatreId,theatreDTO);
        return new ResponseEntity<>(createdTheatreDTO,HttpStatus.CREATED);
    }

    @PostMapping("/theatres/seats")
    public ResponseEntity<TheatreDTO> addTheaterSeat(@RequestBody SeatDTO seatDTO){
        System.out.println("Received SeatDTO: " + seatDTO);
        TheatreDTO theatreDTO = theatreService.addTheaterSeat(seatDTO);
        return new ResponseEntity<>(theatreDTO,HttpStatus.CREATED);
    }
}
