package com.social.bookmyshow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Seat {
    @Id
    private String seatId;
    private SeatType seatType;
    private Long seatNumber;
    private String TheatreId;

    public Seat(Long seatNumber, SeatType seatType, String theatreId) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.TheatreId = theatreId;
    }
}
