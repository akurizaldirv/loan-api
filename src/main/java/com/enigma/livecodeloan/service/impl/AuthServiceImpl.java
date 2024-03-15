package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.*;
import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.request.auth.RegisterCustomerRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public RegisterResponse registerCustomer(RegisterCustomerRequest request) {
        Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        User user = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        UserRole userRole = UserRole.builder()
                .role(role)
                .build();
        user.setRoles(List.of(userRole));

        User createdUser = userRepository.save(user);
        userRole.setUser(createdUser);
        UserRole createdUserRole = userRoleRepository.save(userRole);

        Customer customer = customerService.createCustomer(createdUser, request);

        return AuthMapper.mapToRegisterRes(user);
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role roleStaff = roleService.getOrSave(ERole.ROLE_STAFF);

        User user = AuthMapper.mapToEntity(request, passwordEncoder.encode(request.getPassword()));

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.builder().role(roleCustomer).build());
        roles.add(UserRole.builder().role(roleStaff).build());
        roles.add(UserRole.builder().role(roleAdmin).build());

        user.setRoles(roles);
        User createdUser = userRepository.save(user);

        for (UserRole role : roles) {
            role.setUser(createdUser);
            userRoleRepository.save(role);
        }

        return AuthMapper.mapToRegisterRes(createdUser);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse registerStaff(AuthRequest request) {
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        Role roleStaff = roleService.getOrSave(ERole.ROLE_STAFF);

        User user = AuthMapper.mapToEntity(request, passwordEncoder.encode(request.getPassword()));

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.builder().role(roleCustomer).build());
        roles.add(UserRole.builder().role(roleStaff).build());

        user.setRoles(roles);
        User createdUser = userRepository.save(user);

        for (UserRole role : roles) {
            role.setUser(createdUser);
            userRoleRepository.save(role);
        }

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
