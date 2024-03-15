package com.enigma.livecodeloan.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthRequest {
    @NotBlank(message = "Email cannot be blank")
    private String password;
    @NotBlank(message = "Password cannot be blank")
    private String email;
}
