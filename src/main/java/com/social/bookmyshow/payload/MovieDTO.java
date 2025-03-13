package com.social.bookmyshow.payload;

import com.social.bookmyshow.model.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO {
    private String movieId;
    private String title;
    private List<String> actors;
    private String description;
    private String releaseDate;
    private Double rating;
    private String genre;
    private String duration;
}

