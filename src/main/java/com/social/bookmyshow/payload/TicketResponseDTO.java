package com.social.bookmyshow.payload;

import com.social.bookmyshow.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Date date;
    private String movieTitle;
    private String theatreName;
    private Address address;
    private String bookedSeats;
    private Integer price;
}

