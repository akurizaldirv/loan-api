package com.enigma.livecodeloan.model.request.transaction;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionRequest {
    @Valid
    private IdRequest loanType;
    @Valid
    private IdRequest instalmentType;
    @Valid
    private IdRequest customer;
    @NotNull(message = "Nominal cannot be null")
    @Min(value = 1)
    private Long nominal;
}
