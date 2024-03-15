package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanType, String> {
}
