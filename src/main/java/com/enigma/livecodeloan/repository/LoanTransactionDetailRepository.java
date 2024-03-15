package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.LoanTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoanTransactionDetailRepository extends JpaRepository<LoanTransactionDetail, String> {
    @Query("select ld from LoanTransactionDetail ld where ld.id = :id and ld.loanTransaction.id = :loanId")
    Optional<LoanTransactionDetail> findByIdAndLoanId(String id, String loanId);
}
