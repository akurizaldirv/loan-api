package com.enigma.livecodeloan.model.request.transaction;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ApproveRequest {
    @NotBlank(message = "Loan Transaction ID cannot be blank")
    private String loanTransactionId;
    @NotNull(message = "Interest Rates cannot be null")
    @Min(value = 1)
    @Max(value = 100)
    private Integer interestRates;
}
