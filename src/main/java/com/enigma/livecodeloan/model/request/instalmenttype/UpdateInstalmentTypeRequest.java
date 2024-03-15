package com.enigma.livecodeloan.model.request.instalmenttype;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInstalmentTypeRequest {
    private String id;
    private String instalmentType;
}
