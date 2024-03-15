package com.enigma.livecodeloan.util.mapper;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.util.enums.EInstalmentType;
import jakarta.validation.ValidationException;

public class InstalmentTypeMapper {
    public static InstalmentType mapToEntity(InstalmentTypeRequest request) {
        try {
            EInstalmentType type = EInstalmentType.valueOf(request.getInstalmentType().toUpperCase());
            return InstalmentType.builder()
                    .instalmentType(type)
                    .build();
        } catch (Exception e) {
            throw new ValidationException("Instalment Type is not valid");
        }
    }

    public static InstalmentTypeResponse mapToRes(InstalmentType instalmentType) {
        return InstalmentTypeResponse.builder()
                .id(instalmentType.getId())
                .instalmentType(instalmentType.getInstalmentType().name())
                .build();
    }
}
