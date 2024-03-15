package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
}
