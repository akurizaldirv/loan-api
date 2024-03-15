package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByIdAndStatusTrue(String id);
    Optional<Customer> findByUserEmailAndStatusTrue(String email);
    List<Customer> findAllByStatusTrue();
    @Modifying
    @Query("update Customer c set c.status=false where c.id=:id")
    void softDeleteById(String id);
}

