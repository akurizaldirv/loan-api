package com.enigma.livecodeloan.model.request.transaction;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PayRequest {
    @NotBlank(message = "Loan Transaction Detail ID cannot be blank")
    private String loanTransactionDetailId;
}
