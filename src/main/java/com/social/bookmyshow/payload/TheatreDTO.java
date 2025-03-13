package com.social.bookmyshow.payload;

import com.social.bookmyshow.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreDTO {
    private String theatreId;
    private String theatreName;
    private Address address;
}
