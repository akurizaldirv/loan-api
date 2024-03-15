package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByStatusAndUserEmail(String status, String email);
}
