package com.social.bookmyshow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class Ticket {
    @Id
    private String ticketId;
    private Date date;
    private String movieTitle;
    private String theatreName;
    private Address address;
    private String bookedSeats;
    private Integer price;
    private String showId;
    private String userId;
    @Version
    private Integer version;
}
