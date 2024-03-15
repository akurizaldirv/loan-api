package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.Role;
import com.enigma.livecodeloan.repository.RoleRepository;
import com.enigma.livecodeloan.service.RoleService;
import com.enigma.livecodeloan.util.enums.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> newRole = repository.findByRole(role);

        return newRole.orElseGet(() -> repository.saveAndFlush(
                Role.builder()
                        .role(role)
                        .build()
        ));
    }
}
