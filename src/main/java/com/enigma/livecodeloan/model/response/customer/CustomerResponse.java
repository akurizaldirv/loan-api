package com.enigma.livecodeloan.model.response.customer;

import com.zaxxer.hikari.util.ClockSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phone;
    private String status;
    private List<String> roles;
}
