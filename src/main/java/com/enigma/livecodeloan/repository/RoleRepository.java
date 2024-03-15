package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.Role;
import com.enigma.livecodeloan.util.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
