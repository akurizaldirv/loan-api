package com.enigma.livecodeloan.model.request.transaction;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RejectRequest {
    @NotBlank(message = "Loan Transaction ID cannot be blank")
    private String loanTransactionId;
}
