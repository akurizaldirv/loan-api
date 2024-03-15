package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.request.loantype.LoanTypeRequest;
import com.enigma.livecodeloan.model.request.loantype.UpdateLoanTypeRequest;
import com.enigma.livecodeloan.model.response.loantype.LoanTypeResponse;

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
