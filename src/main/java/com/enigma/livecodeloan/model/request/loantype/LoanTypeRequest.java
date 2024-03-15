package com.enigma.livecodeloan.model.request.loantype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanTypeRequest {
    private String type;
    private Long maxLoan;
}
