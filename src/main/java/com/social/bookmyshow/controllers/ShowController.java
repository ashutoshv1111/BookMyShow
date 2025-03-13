package com.social.bookmyshow.controllers;

import com.social.bookmyshow.model.Show;
import com.social.bookmyshow.payload.ShowDTO;
import com.social.bookmyshow.payload.ShowSeatsDTO;
import com.social.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShowController {
    @Autowired
    private ShowService showService;

    @GetMapping("/show/{movieId}")
    public ResponseEntity<List<ShowDTO>> getShow(@PathVariable String movieId) {
        List<ShowDTO> showDTOs = showService.getShow(movieId);
        return new ResponseEntity<>(showDTOs,HttpStatus.OK);
    }
    @PostMapping("/show")
    public ResponseEntity<ShowDTO> createShow(@RequestBody ShowDTO showDTO) {
        ShowDTO createdShowDTO = showService.createShow(showDTO);
        return new ResponseEntity<>(createdShowDTO,HttpStatus.CREATED);
    }
    @PostMapping("/associateShowSeats")
    public ResponseEntity<ShowDTO> associateShowSeats(@RequestBody ShowSeatsDTO showSeatsDTO) {
            ShowDTO showDTO = showService.associateShowSeats(showSeatsDTO);
            return new ResponseEntity<>(showDTO, HttpStatus.CREATED);
    }
}
