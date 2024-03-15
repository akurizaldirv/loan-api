package com.enigma.livecodeloan.model.response;

import com.zaxxer.hikari.util.ClockSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
