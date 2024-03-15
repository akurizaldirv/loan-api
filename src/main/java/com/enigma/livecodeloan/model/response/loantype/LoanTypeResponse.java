package com.enigma.livecodeloan.model.response.loantype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanTypeResponse {
    private String id;
    private String type;
    private Long maxLoan;
}
