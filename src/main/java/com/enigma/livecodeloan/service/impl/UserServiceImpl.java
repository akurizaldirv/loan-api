package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.response.auth.UserResponse;
import com.enigma.livecodeloan.repository.UserRepository;
import com.enigma.livecodeloan.service.UserService;
import com.enigma.livecodeloan.util.enums.ERole;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUserId(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        List<ERole> roles = user.getRoles().stream().map(userRole -> {
            return ERole.valueOf(userRole.getRole().getRole().name());
        }).toList();


        return AppUser.builder()
                .email(user.getEmail())
                .roles(roles)
                .password(user.getPassword())
                .id(user.getId())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        List<ERole> roles = user.getRoles().stream().map(userRole -> {
            return ERole.valueOf(userRole.getRole().getRole().name());
        }).toList();


        return AppUser.builder()
                .email(user.getEmail())
                .roles(roles)
                .password(user.getPassword())
                .id(user.getId())
                .build();
    }

    @Override
    public UserResponse getById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        List<String> roles = user.getRoles().stream().map(role ->
                role.getRole().getRole().name()
        ).toList();

        return UserResponse.builder()
                .email(user.getEmail())
                .role(roles)
                .build();
    }

    @Override
    public User getUserByCustomerId(String id) {
        return userRepository.findByCustomerId(id).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
    }
}
