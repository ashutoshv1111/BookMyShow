package com.social.bookmyshow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Show {
    private String movieId;
    private String screenId;
    private Integer totalNoOfTickets;
    private List<Ticket> tickets;
    private Time startTime;
    private Time endTime;
    private Date date;
}
