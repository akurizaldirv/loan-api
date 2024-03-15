package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstalmentTypeRepository extends JpaRepository<InstalmentType, String> {
}
