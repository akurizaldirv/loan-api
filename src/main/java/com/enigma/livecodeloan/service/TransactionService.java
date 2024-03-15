package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.LoanTransaction;
import com.enigma.livecodeloan.model.entity.LoanTransactionDetail;
import com.enigma.livecodeloan.model.request.transaction.ApproveRequest;
import com.enigma.livecodeloan.model.request.transaction.PayRequest;
import com.enigma.livecodeloan.model.request.transaction.RejectRequest;
import com.enigma.livecodeloan.model.request.transaction.TransactionRequest;
import com.enigma.livecodeloan.model.response.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    LoanTransaction getTrxById(String id);
    LoanTransactionDetail getTrxDetailById(String id);
    LoanTransactionDetail getTrxDetailByIdAndLoanId(String id, String loanId);
    TransactionResponse getById(String id);
    List<TransactionResponse> getAll();
    TransactionResponse approve(String adminId, ApproveRequest approveRequest);
    TransactionResponse reject(String adminId, RejectRequest rejectRequest);
    TransactionResponse pay(String trxId, PayRequest payRequest);
}
