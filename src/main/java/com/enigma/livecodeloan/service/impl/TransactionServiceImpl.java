package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.*;
import com.enigma.livecodeloan.model.request.transaction.ApproveRequest;
import com.enigma.livecodeloan.model.request.transaction.PayRequest;
import com.enigma.livecodeloan.model.request.transaction.RejectRequest;
import com.enigma.livecodeloan.model.request.transaction.TransactionRequest;
import com.enigma.livecodeloan.model.response.transaction.TransactionDetailResponse;
import com.enigma.livecodeloan.model.response.transaction.TransactionResponse;
import com.enigma.livecodeloan.repository.LoanTransactionDetailRepository;
import com.enigma.livecodeloan.repository.LoanTransactionRepository;
import com.enigma.livecodeloan.service.*;
import com.enigma.livecodeloan.util.enums.ApprovalStatus;
import com.enigma.livecodeloan.util.enums.EInstalmentType;
import com.enigma.livecodeloan.util.enums.LoanStatus;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.enigma.livecodeloan.util.enums.EInstalmentType.*;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailRepository loanTransactionDetailRepository;

    private final InstalmentTypeService instalmentTypeService;
    private final CustomerService customerService;
    private final UserService userService;
    private final LoanTypeService loanTypeService;

    @Override
    public TransactionResponse create(TransactionRequest request) {
        Customer customer = customerService.getCustomerById(request.getCustomer().getId());
        InstalmentType instalmentType = instalmentTypeService.getInstalmentTypeById(request.getInstalmentType().getId());
        LoanType loanType = loanTypeService.getLoanTypeById(request.getLoanType().getId());

        if (request.getNominal() > loanType.getMaxLoan()) {
            throw new ValidationException("Nominal cannot more than max loan type");
        }

        LoanTransaction loanTransaction = TransactionMapper.mapToTrxEntity(request, customer, instalmentType, loanType);

        return TransactionMapper.mapToTrxRes(loanTransactionRepository.save(loanTransaction), null);
    }

    @Override
    public LoanTransaction getTrxById(String id) {
        return loanTransactionRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Loan Transaction not found")
        );
    }

    @Override
    public LoanTransactionDetail getTrxDetailById(String id) {
        return loanTransactionDetailRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Loan Transaction Detail not found")
        );
    }

    @Override
    public LoanTransactionDetail getTrxDetailByIdAndLoanId(String id, String loanId) {
        return loanTransactionDetailRepository.findByIdAndLoanId(id, loanId).orElseThrow(
                () -> new DataNotFoundException("Loan Transaction Detail not found")
        );
    }

    @Override
    public TransactionResponse getById(String id) {
        LoanTransaction loanTransaction = this.getTrxById(id);
        List<TransactionDetailResponse> detailResponses = loanTransaction.getLoanTransactionDetails()
                .stream().map(TransactionMapper::mapToDetailRes).toList();
        System.out.println(" --------- " + loanTransaction.getLoanTransactionDetails());
        System.out.println(" --------- " + detailResponses);
        return TransactionMapper.mapToTrxRes(loanTransaction, detailResponses);
    }

    @Override
    public List<TransactionResponse> getAll() {
        List<LoanTransaction> loanTransactions = loanTransactionRepository.findAll();

        return loanTransactions.stream().map(
                loanTransaction -> {
                    List<TransactionDetailResponse> trxDetailRes = loanTransaction.getLoanTransactionDetails()
                            .stream().map(TransactionMapper::mapToDetailRes).toList();

                    return TransactionMapper.mapToTrxRes(loanTransaction, trxDetailRes);
                }
        ).toList();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse approve(String adminId, ApproveRequest approveRequest) {
        User user = userService.getUserByCustomerId(adminId);
        LoanTransaction loanTransaction = this.getTrxById(approveRequest.getLoanTransactionId());

        if (loanTransaction.getApprovalStatus() == ApprovalStatus.REJECTED) throw new ValidationException("Cannot Approve rejected Loan Request");
        if (loanTransaction.getApprovalStatus() == ApprovalStatus.APPROVED) throw new ValidationException("Already Approved");

        loanTransaction.setApprovedBy(user.getEmail());
        loanTransaction.setApprovedAt(Instant.now().getEpochSecond());
        loanTransaction.setApprovalStatus(ApprovalStatus.APPROVED);
        loanTransaction.setUpdatedAt(Instant.now().getEpochSecond());

        Integer totalTransactionDetail = switch (loanTransaction.getInstalmentType().getInstalmentType()) {
            case ONE_MONTH -> 1;
            case THREE_MONTHS -> 3;
            case SIXTH_MONTHS -> 6;
            case NINE_MONTHS -> 9;
            case TWELVE_MONTHS -> 12;
            default -> 12;
        };
        Double dividedNominal = loanTransaction.getNominal()/totalTransactionDetail;
        Double interestNominal = loanTransaction.getNominal() * (approveRequest.getInterestRates()/100.0);
        Double payNominal = dividedNominal + interestNominal;


        List<LoanTransactionDetail> loanTransactionDetails = new ArrayList<>();
        for (int i = 0; i < totalTransactionDetail; i++) {
            loanTransactionDetails.add(
                    LoanTransactionDetail.builder()
                            .createdAt(Instant.now().getEpochSecond())
                            .nominal(payNominal)
                            .loanStatus(LoanStatus.UNPAID)
                            .loanTransaction(loanTransaction)
                            .build()
            );
        }

        loanTransaction.setLoanTransactionDetails(loanTransactionDetails);
        loanTransactionRepository.save(loanTransaction);

        List<TransactionDetailResponse> transactionDetailResponses = loanTransactionDetails.stream().map(
                TransactionMapper::mapToDetailRes
        ).toList();
        return TransactionMapper.mapToTrxRes(loanTransaction, transactionDetailResponses);
    }

    @Override
    public TransactionResponse reject(String adminId, RejectRequest rejectRequest) {
        User user = userService.getUserByCustomerId(adminId);
        LoanTransaction loanTransaction = this.getTrxById(rejectRequest.getLoanTransactionId());

        if (loanTransaction.getApprovalStatus() == ApprovalStatus.APPROVED) throw new ValidationException("Cannot Reject Approved Loan Request");
        if (loanTransaction.getApprovalStatus() == ApprovalStatus.REJECTED) throw new ValidationException("Already Rejected");

        loanTransaction.setApprovedBy(user.getEmail());
        loanTransaction.setApprovedAt(Instant.now().getEpochSecond());
        loanTransaction.setApprovalStatus(ApprovalStatus.REJECTED);
        loanTransaction.setUpdatedAt(Instant.now().getEpochSecond());

        loanTransactionRepository.save(loanTransaction);

        return TransactionMapper.mapToTrxRes(loanTransaction, null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse pay(String trxId, PayRequest payRequest) {
        LoanTransaction loanTransaction = this.getTrxById(trxId);
        if (loanTransaction.getApprovalStatus() == null) throw new ValidationException("Loan Request not approved yet");
        if (loanTransaction.getApprovalStatus() == ApprovalStatus.REJECTED) throw new ValidationException("Loan Request already rejected");
        loanTransaction.setUpdatedAt(Instant.now().getEpochSecond());

        LoanTransactionDetail loanTransactionDetail = this.getTrxDetailByIdAndLoanId(payRequest.getLoanTransactionDetailId(), trxId);
        loanTransactionDetail.setTransactionDate(Instant.now().getEpochSecond());
        loanTransactionDetail.setUpdatedAt(Instant.now().getEpochSecond());
        loanTransactionDetail.setLoanStatus(LoanStatus.PAID);

        loanTransactionDetailRepository.save(loanTransactionDetail);
        loanTransactionRepository.save(loanTransaction);

        return this.getById(trxId);
    }
}
