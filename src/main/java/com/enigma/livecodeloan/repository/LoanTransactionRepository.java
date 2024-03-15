package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, String> {
}
