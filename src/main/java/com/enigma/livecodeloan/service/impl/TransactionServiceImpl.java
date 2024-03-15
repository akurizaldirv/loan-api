package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.entity.LoanTransaction;
import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.request.transaction.TransactionRequest;
import com.enigma.livecodeloan.model.response.transaction.TransactionDetailResponse;
import com.enigma.livecodeloan.model.response.transaction.TransactionResponse;
import com.enigma.livecodeloan.repository.LoanTransactionDetailRepository;
import com.enigma.livecodeloan.repository.LoanTransactionRepository;
import com.enigma.livecodeloan.service.CustomerService;
import com.enigma.livecodeloan.service.InstalmentTypeService;
import com.enigma.livecodeloan.service.LoanTypeService;
import com.enigma.livecodeloan.service.TransactionService;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.TransactionMapper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailRepository loanTransactionDetailRepository;

    private final InstalmentTypeService instalmentTypeService;
    private final CustomerService customerService;
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
    public TransactionResponse getById(String id) {
        LoanTransaction loanTransaction = this.getTrxById(id);
        List<TransactionDetailResponse> detailResponses = loanTransaction.getLoanTransactionDetails()
                .stream().map(TransactionMapper::mapToDetailRes).toList();

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
}
