package com.enigma.livecodeloan.util.mapper;

import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.request.loan.LoanTypeRequest;
import com.enigma.livecodeloan.model.response.loan.LoanTypeResponse;

public class LoanTypeMapper {
    public static LoanType mapToEntity(LoanTypeRequest request) {
        return LoanType.builder()
                .type(request.getType())
                .maxLoan(request.getMaxLoan().doubleValue())
                .build();
    }

    public static LoanTypeResponse mapToRes(LoanType loanType) {
        return LoanTypeResponse.builder()
                .type(loanType.getType())
                .maxLoan(loanType.getMaxLoan().longValue())
                .id(loanType.getId())
                .build();
    }
}
