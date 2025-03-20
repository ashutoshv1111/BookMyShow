package com.social.bookmyshow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Theatre {
    @Id
    private String theatreId;
    @JsonProperty
    private String theatreName;
    @JsonProperty
    private Address address;
    private List<String> seatIds;
    private List<String> showIds;
}
