package com.enigma.livecodeloan.repository;

import com.enigma.livecodeloan.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<User, String> {
    Optional<User> findAppUserByEmail(String email);
}
