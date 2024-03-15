package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.entity.Role;
import com.enigma.livecodeloan.model.entity.UserRole;
import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.response.LoginResponse;
import com.enigma.livecodeloan.model.response.RegisterResponse;
import com.enigma.livecodeloan.repository.AppUserRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRoleRepository userRoleRepository;
    private final AppUserRepository appUserRepository;

    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse register(AuthRequest authRequest) {
        Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        AppUser appUser = AuthMapper.mapToEntity(authRequest, passwordEncoder.encode(authRequest.getPassword()));
        UserRole userRole = UserRole.builder()
                .role(role)
                .build();
        appUser.setRoles(List.of(userRole));

        AppUser createdAppUser = appUserRepository.save(appUser);
        userRole.setAppUser(createdAppUser);
        UserRole createdUserRole = userRoleRepository.save(userRole);

        Customer customer = customerService.create(createdAppUser);

        return AuthMapper.mapToRegisterRes(appUser);
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail().toLowerCase(),
                        authRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return AuthMapper.mapToLoginRes(appUser, token);
    }
}
