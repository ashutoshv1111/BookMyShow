package com.social.bookmyshow.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.social.bookmyshow.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class ShowSeatsDTO {
    @Id
    @JsonProperty("showId")
    private String showId;
    @JsonProperty("SILVERSeatPrice")
    private Integer SILVERSeatPrice;
    @JsonProperty("GOLSeatPrice")
    private Integer GOLSeatPrice;
}
