package com.social.bookmyshow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Movie {
    @Id
    private String movieId;
    private String title;
    private List<String> actors;
    private String description;
    private String releaseDate;
    private Double rating;
    private String genre;
    private String duration;
    private List<String> showIds;
}


