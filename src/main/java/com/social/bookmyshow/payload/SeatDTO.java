package com.social.bookmyshow.payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.social.bookmyshow.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class SeatDTO {
    private String theatreId;
    private Integer noOfSeatsInRow;
    @JsonProperty("SILVERSeatPrice")
    private Integer SILVERSeatPrice;
    @JsonProperty("GOLSeatPrice")
    private Integer GOLSeatPrice;
}
