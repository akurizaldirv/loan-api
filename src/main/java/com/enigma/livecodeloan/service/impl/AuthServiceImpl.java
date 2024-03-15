package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.*;
import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.response.auth.LoginResponse;
import com.enigma.livecodeloan.model.response.auth.RegisterResponse;
import com.enigma.livecodeloan.repository.UserRepository;
import com.enigma.livecodeloan.repository.UserRoleRepository;
import com.enigma.livecodeloan.service.AuthService;
import com.enigma.livecodeloan.service.CustomerService;
import com.enigma.livecodeloan.service.RoleService;
import com.enigma.livecodeloan.util.enums.ERole;
import com.enigma.livecodeloan.util.mapper.AuthMapper;
import com.enigma.livecodeloan.util.security.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse register(AuthRequest request) {
        Set<String> setRoles = new HashSet<>(request.getRoles().stream().map(String::toUpperCase).toList());
        List<ERole> eRoles = setRoles.stream().map(role -> {
            try {
                return ERole.valueOf(role);
            } catch (Exception e) {
                throw new ValidationException("Invalid Role");
            }
        }).toList();
        List<Role> roles = eRoles.stream().map(roleService::getOrSave).toList();

        User user = AuthMapper.mapToEntity(request, passwordEncoder.encode(request.getPassword()));

        List<UserRole> userRoles = new ArrayList<>();
        roles.forEach(role -> userRoles.add(UserRole.builder().role(role).build()));

        user.setRoles(userRoles);
        User createdUser = userRepository.save(user);

        for (UserRole role : userRoles) {
            role.setUser(createdUser);
            userRoleRepository.save(role);
        }
        customerService.create(createdUser, request);

        return AuthMapper.mapToRegisterRes(createdUser);
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail().toLowerCase(),
                        authRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser user = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);

        return AuthMapper.mapToLoginRes(user, token);
    }
}
