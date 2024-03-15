package com.enigma.livecodeloan.model.response.transaction;

import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.entity.LoanTransactionDetail;
import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.response.customer.CustomerResponse;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.model.response.loantype.LoanTypeResponse;
import com.enigma.livecodeloan.util.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {
    private String id;
    private LoanTypeResponse loanType;
    private InstalmentTypeResponse instalmentType;
    private CustomerResponse customer;
    private Long nominal;
    private Long approvedAt;
    private String approvedBy;
    private ApprovalStatus approvalStatus;
    private List<TransactionDetailResponse> transactionDetailResponses;
    private Long createdAt;
    private Long updatedAt;
}
