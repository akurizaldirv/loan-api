package com.enigma.livecodeloan.model.request.instalmenttype;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class InstalmentTypeRequest {
    @NotBlank(message = "Instalment Type cannot be blank")
    private String instalmentType;
}
