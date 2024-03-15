package com.enigma.livecodeloan.model.request.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateLoanTypeRequest {
    private String id;
    private String type;
    private Long maxLoan;
}