package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.*;
import com.enigma.livecodeloan.model.response.auth.UserResponse;
import com.enigma.livecodeloan.repository.LoanTypeRepository;
import com.enigma.livecodeloan.repository.UserRepository;
import com.enigma.livecodeloan.util.enums.ERole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void loadUserByUserId_ExistId_ReturnAppUser() {
        AppUser dummyAppUser = AppUser.builder()
                .id("appuser123")
                .password("password123")
                .email("email@mail.id")
                .roles(List.of(ERole.ROLE_ADMIN))
                .build();

        UserRole dummyUserRole = UserRole.builder()
                .id("userrole123")
                .role(Role.builder()
                        .role(ERole.ROLE_ADMIN)
                        .build())
                .build();

        User dummyUser = User.builder()
                .email(dummyAppUser.getEmail())
                .password(dummyAppUser.getPassword())
                .id(dummyAppUser.getId())
                .roles(List.of(dummyUserRole))
                .build();

        when(userRepository.findById(dummyUser.getId())).thenReturn(Optional.of(dummyUser));

        UserDetails actual = userService.loadUserByUserId(dummyUser.getId());
        verify(userRepository, times(1)).findById(dummyUser.getId());

        Assertions.assertEquals(dummyAppUser.getRoles().size(), actual.getAuthorities().size());
        Assertions.assertEquals(dummyAppUser.getPassword(), actual.getPassword());
    }

    @Test
    void loadUserByUsername() {
        AppUser dummyAppUser = AppUser.builder()
                .id("appuser123")
                .password("password123")
                .email("email@mail.id")
                .roles(List.of(ERole.ROLE_ADMIN))
                .build();

        UserRole dummyUserRole = UserRole.builder()
                .id("userrole123")
                .role(Role.builder()
                        .role(ERole.ROLE_ADMIN)
                        .build())
                .build();

        User dummyUser = User.builder()
                .email(dummyAppUser.getEmail())
                .password(dummyAppUser.getPassword())
                .id(dummyAppUser.getId())
                .roles(List.of(dummyUserRole))
                .build();

        when(userRepository.findUserByEmail(dummyUser.getEmail())).thenReturn(Optional.of(dummyUser));

        UserDetails actual = userService.loadUserByUsername(dummyUser.getEmail());
        verify(userRepository, times(1)).findUserByEmail(dummyUser.getEmail());

        Assertions.assertEquals(dummyAppUser.getRoles().size(), actual.getAuthorities().size());
        Assertions.assertEquals(dummyAppUser.getPassword(), actual.getPassword());
    }

    @Test
    void getById() {
        UserResponse dummyRes = UserResponse.builder()
                .role(List.of(ERole.ROLE_ADMIN.name()))
                .email("email@mail.id")
                .build();

        UserRole dummyUserRole = UserRole.builder()
                .role(Role.builder()
                        .role(ERole.ROLE_ADMIN)
                        .build())
                .build();

        User dummyUser = User.builder()
                .id("user123")
                .email(dummyRes.getEmail())
                .roles(List.of(dummyUserRole))
                .build();

        when(userRepository.findById(dummyUser.getId())).thenReturn(Optional.of(dummyUser));

        UserResponse actual = userService.getById(dummyUser.getId());
        verify(userRepository, times(1)).findById(dummyUser.getId());

        Assertions.assertEquals(dummyRes.getEmail(), actual.getEmail());
        Assertions.assertEquals(dummyRes.getRole(), actual.getRole());
    }

    @Test
    void getUserByCustomerId() {
        UserRole dummyUserRole = UserRole.builder()
                .role(Role.builder()
                        .role(ERole.ROLE_ADMIN)
                        .build())
                .build();

        User dummyUser = User.builder()
                .id("user123")
                .customer(Customer.builder()
                        .id("customer123")
                        .build())
                .email("mail@mail.id")
                .roles(List.of(dummyUserRole))
                .build();

        when(userRepository.findByCustomerId(dummyUser.getCustomer().getId())).thenReturn(Optional.of(dummyUser));

        User actual = userService.getUserByCustomerId(dummyUser.getCustomer().getId());
        verify(userRepository, times(1)).findByCustomerId(dummyUser.getCustomer().getId());

        Assertions.assertEquals(dummyUser.getEmail(), actual.getEmail());
        Assertions.assertEquals(dummyUser.getId(), actual.getId());
        Assertions.assertEquals(dummyUser.getPassword(), actual.getPassword());
    }
}