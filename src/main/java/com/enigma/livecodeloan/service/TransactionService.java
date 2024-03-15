package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.LoanTransaction;
import com.enigma.livecodeloan.model.request.transaction.ApproveRequest;
import com.enigma.livecodeloan.model.request.transaction.RejectRequest;
import com.enigma.livecodeloan.model.request.transaction.TransactionRequest;
import com.enigma.livecodeloan.model.response.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    LoanTransaction getTrxById(String id);
    TransactionResponse getById(String id);
    List<TransactionResponse> getAll();
    TransactionResponse approve(String adminId, ApproveRequest approveRequest);
    TransactionResponse reject(String adminId, RejectRequest rejectRequest);
}
