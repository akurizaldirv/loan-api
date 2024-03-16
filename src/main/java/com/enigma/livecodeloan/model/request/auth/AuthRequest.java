package com.enigma.livecodeloan.model.request.auth;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthRequest {
    @NotBlank(message = "Email cannot be blank")
    private String password;
    @NotBlank(message = "Password cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;
    @NotNull(message = "Date of Birth cannot be blank")
    private Date dateOfBirth;
    @NotBlank(message = "Phone cannot be blank")
    private String phone;
    @NotNull(message = "Role cannot be null")
    @NotEmpty(message = "Role cannot be blank")
    private List<@NotNull(message = "Role cannot be null") String> roles;
}
