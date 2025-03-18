package com.social.bookmyshow.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class AppUser {
    @Id
    private String userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    private Role role;
    private Address address;
    private List<String> ticketIds;

    public AppUser(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(max = 50) @Email String email, String encode) {
    }
}
