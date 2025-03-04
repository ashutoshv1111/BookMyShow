package com.social.bookmyshow.payload;


import com.social.bookmyshow.model.Address;
import com.social.bookmyshow.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserDTO {
    @Id
    private String userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    private List<String> ticketIds;
    private Role role;
    private Address address;
}
