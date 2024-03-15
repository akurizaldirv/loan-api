package com.enigma.livecodeloan.model.request.transaction;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class IdRequest {
    @NotBlank(message = "ID Cannot be blank")
    private String id;
}
