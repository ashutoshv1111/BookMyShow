package com.social.bookmyshow.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
public class Screen {
    @Id
    private String screenId;
    private String screenName;
    private String theatreId;
    private List<String> seatIds;

}
