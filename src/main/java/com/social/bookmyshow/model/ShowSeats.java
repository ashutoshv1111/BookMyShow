package com.social.bookmyshow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ShowSeats {
    @Id
    private String showSeatsId;
    private SeatType seatType;
    private Long seatNumber;
    private int price;
    private Date bookedDate;
    private boolean booked;
    private String showId;
    @Version
    private int version;
}
