package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.util.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByIdAndStatus(String id, EStatus status);
    Optional<Customer> findByUserEmailAndStatus(String email, EStatus status);
    List<Customer> findAllByStatus(EStatus eStatus);
    @Modifying
    @Query("update Customer c set c.status=:status where c.id=:id")
    void updateStatus(String id, EStatus status);
}

