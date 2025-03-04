package com.social.bookmyshow.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor

public class MovieDTO {
    @Id
    private String movieId;
    private String title;
    private List<String> actors;
    private String description;
    private String releaseDate;
    private Double rating;
    private String genre;
    private String duration;
}

