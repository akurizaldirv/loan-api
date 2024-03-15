package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.loan.LoanTypeRequest;
import com.enigma.livecodeloan.model.request.loan.UpdateLoanTypeRequest;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.model.response.loan.LoanTypeResponse;

import java.util.List;

public interface LoanTypeService {
    LoanTypeResponse create(LoanTypeRequest request);
    LoanType getLoanTypeById(String id);
    LoanTypeResponse getById(String id);
    List<LoanTypeResponse> getAll();
    LoanTypeResponse update(UpdateLoanTypeRequest request);
    void delete(String id);
    void throwIfIdNotExist(String id);
}
