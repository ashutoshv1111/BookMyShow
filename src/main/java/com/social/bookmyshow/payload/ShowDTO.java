package com.social.bookmyshow.payload;

import com.social.bookmyshow.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ShowDTO {
    private String movieId;
    private String screenId;
    private Integer totalNoOfTickets;
    private List<Ticket> tickets;
    private Time startTime;
    private Time endTime;
    private Date date;
}
