package com.social.bookmyshow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Ticket {
    @Id
    private String ticketId;
    private String showId;
    private String userId;
    private List<String> seatIds;
    private Long price;

}
