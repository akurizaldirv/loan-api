package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;

import java.util.List;

public interface InstalmentTypeService {
    InstalmentTypeResponse create(InstalmentTypeRequest request);
    InstalmentType getInstalmentTypeById(String id);
    InstalmentTypeResponse getById(String id);
    List<InstalmentTypeResponse> getAll();
    InstalmentTypeResponse update(UpdateInstalmentTypeRequest request);
    void delete(String id);
    void throwIfIdNotExist(String id);
}
