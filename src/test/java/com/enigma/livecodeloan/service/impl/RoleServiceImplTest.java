package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.Role;
import com.enigma.livecodeloan.repository.LoanTypeRepository;
import com.enigma.livecodeloan.repository.RoleRepository;
import com.enigma.livecodeloan.util.enums.ERole;
import com.enigma.livecodeloan.util.mapper.LoanTypeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {
    private RoleRepository roleRepository;
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleServiceImpl(roleRepository);
    }
    @Test
    void getOrSave_ExistRole_ReturnExistRole() {
        ERole role = ERole.ROLE_ADMIN;
        Role dummyRole = Role.builder()
                .id("role123")
                .role(role)
                .build();

        when(roleRepository.findByRole(role)).thenReturn(Optional.of(dummyRole));

        Role actual = roleService.getOrSave(role);
        verify(roleRepository, times(1)).findByRole(role);
        verify(roleRepository, never()).saveAndFlush(any(Role.class));
    }
    @Test
    void getOrSave_NotExistRole_ReturnNewRole() {
        ERole role = ERole.ROLE_ADMIN;
        Role dummyRole = Role.builder()
                .id("role123")
                .role(role)
                .build();

        when(roleRepository.findByRole(role)).thenReturn(Optional.empty());
        when(roleRepository.saveAndFlush(any(Role.class))).thenReturn(dummyRole);

        Role actual = roleService.getOrSave(role);
        verify(roleRepository, times(1)).findByRole(role);
        verify(roleRepository, times(1)).saveAndFlush(any(Role.class));

        Assertions.assertEquals(dummyRole.getRole(), actual.getRole());
        Assertions.assertEquals(dummyRole.getId(), actual.getId());
    }
}