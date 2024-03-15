package com.enigma.livecodeloan.model.request.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateCustomerRequest {
    @NotBlank(message = "Customer ID cannot be blank")
    private String id;
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;
    @NotNull(message = "Date of Birth cannot be blank")
    private Date dateOfBirth;
    @NotBlank(message = "Phone cannot be blank")
    private String phone;
    @NotNull(message = "Status must be 0 or 1")
    private Integer status;
}
