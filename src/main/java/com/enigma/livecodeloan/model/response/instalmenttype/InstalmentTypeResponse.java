package com.enigma.livecodeloan.model.response.instalmenttype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class InstalmentTypeResponse {
    private String id;
    private String instalmentType;
}
