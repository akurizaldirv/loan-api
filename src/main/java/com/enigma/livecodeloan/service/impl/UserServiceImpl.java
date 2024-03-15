package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.response.auth.UserResponse;
import com.enigma.livecodeloan.repository.AppUserRepository;
import com.enigma.livecodeloan.service.UserService;
import com.enigma.livecodeloan.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUserId(String id) {
        User user = appUserRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        return User.builder()
                .email(user.getUsername())
                .id(user.getId())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = appUserRepository.findAppUserByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        return User.builder()
                .email(user.getUsername())
                .id(user.getId())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public UserResponse getById(String id) {
        User user = appUserRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        List<String> roles = user.getRoles().stream().map(role ->
                role.getRole().getRole().name()
        ).toList();

        return UserResponse.builder()
                .email(user.getUsername())
                .role(roles)
                .build();
    }
}
