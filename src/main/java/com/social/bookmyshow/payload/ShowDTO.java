package com.social.bookmyshow.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.internal.connection.Time;
import com.social.bookmyshow.model.Movie;
import com.social.bookmyshow.model.Seat;
import com.social.bookmyshow.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowDTO {
    @JsonProperty("startTime")
    private LocalTime startTime;
    @JsonProperty("endTime")
    private LocalTime endTime;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("movieId")
    private String movieId;
    @JsonProperty("theatreId")
    private String theatreId;
}
