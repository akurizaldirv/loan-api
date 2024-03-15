package com.enigma.livecodeloan.util.mapper;

import com.enigma.livecodeloan.model.entity.*;
import com.enigma.livecodeloan.model.request.transaction.TransactionRequest;
import com.enigma.livecodeloan.model.response.transaction.TransactionDetailResponse;
import com.enigma.livecodeloan.model.response.transaction.TransactionResponse;

import java.time.Instant;
import java.util.List;

public class TransactionMapper {
    public static LoanTransaction mapToTrxEntity(TransactionRequest transactionRequest, Customer customer, InstalmentType instalmentType, LoanType loanType) {
        return LoanTransaction.builder()
                .customer(customer)
                .nominal(transactionRequest.getNominal().doubleValue())
                .instalmentType(instalmentType)
                .loanType(loanType)
                .createdAt(Instant.now().getEpochSecond())
                .build();
    }

    public static TransactionResponse mapToTrxRes(LoanTransaction loanTransaction, List<TransactionDetailResponse> transactionDetailResponses) {
        return TransactionResponse.builder()
                .id(loanTransaction.getId())
                .approvalStatus(loanTransaction.getApprovalStatus())
                .approvedAt(loanTransaction.getApprovedAt())
                .approvedBy(loanTransaction.getApprovedBy())
                .createdAt(loanTransaction.getCreatedAt())
                .customer(CustomerMapper.mapToRes(loanTransaction.getCustomer()))
                .instalmentType(InstalmentTypeMapper.mapToRes(loanTransaction.getInstalmentType()))
                .loanType(LoanTypeMapper.mapToRes(loanTransaction.getLoanType()))
                .nominal(loanTransaction.getNominal().longValue())
                .transactionDetailResponses(transactionDetailResponses)
                .updatedAt(loanTransaction.getUpdatedAt())
                .build();
    }

    public static TransactionDetailResponse mapToDetailRes(LoanTransactionDetail transactionDetail) {
        return TransactionDetailResponse.builder()
                .createdAt(transactionDetail.getCreatedAt())
                .updatedAt(transactionDetail.getUpdatedAt())
                .nominal(transactionDetail.getNominal())
                .transactionDate(transactionDetail.getTransactionDate())
                .id(transactionDetail.getId())
                .loanStatus(transactionDetail.getLoanStatus().name())
                .build();
    }
}
