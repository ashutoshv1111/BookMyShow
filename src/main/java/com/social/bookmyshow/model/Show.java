package com.social.bookmyshow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.internal.connection.Time;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class Show {
    @Id
    private String showId;
    @JsonProperty
    private LocalTime startTime;
    @JsonProperty
    private LocalTime endTime;
    @JsonProperty
    private Date date;
    @JsonProperty
    private int rateIncreaseMultiplier;
    private String movieId;
    private String theatreId;
    private List<String> showSeatIds;
    private List<String> ticketIds;

}
