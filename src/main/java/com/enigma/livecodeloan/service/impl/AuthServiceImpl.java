package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.entity.Role;
import com.enigma.livecodeloan.model.entity.UserRole;
import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.request.RegisterCustomerRequest;
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

import java.util.ArrayList;
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
    public RegisterResponse registerCustomer(RegisterCustomerRequest request) {
        Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        AppUser appUser = AppUser.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        UserRole userRole = UserRole.builder()
                .role(role)
                .build();
        appUser.setRoles(List.of(userRole));

        AppUser createdAppUser = appUserRepository.save(appUser);
        userRole.setAppUser(createdAppUser);
        UserRole createdUserRole = userRoleRepository.save(userRole);

        Customer customer = customerService.createCustomer(createdAppUser, request);

        return AuthMapper.mapToRegisterRes(appUser);
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role roleStaff = roleService.getOrSave(ERole.ROLE_STAFF);

        AppUser appUser = AuthMapper.mapToEntity(request, passwordEncoder.encode(request.getPassword()));

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.builder().role(roleCustomer).build());
        roles.add(UserRole.builder().role(roleStaff).build());
        roles.add(UserRole.builder().role(roleAdmin).build());

        appUser.setRoles(roles);
        AppUser createdAppUser = appUserRepository.save(appUser);

        for (UserRole role : roles) {
            role.setAppUser(createdAppUser);
            userRoleRepository.save(role);
        }

        return AuthMapper.mapToRegisterRes(createdAppUser);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse registerStaff(AuthRequest request) {
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        Role roleStaff = roleService.getOrSave(ERole.ROLE_STAFF);

        AppUser appUser = AuthMapper.mapToEntity(request, passwordEncoder.encode(request.getPassword()));

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.builder().role(roleCustomer).build());
        roles.add(UserRole.builder().role(roleStaff).build());

        appUser.setRoles(roles);
        AppUser createdAppUser = appUserRepository.save(appUser);

        for (UserRole role : roles) {
            role.setAppUser(createdAppUser);
            userRoleRepository.save(role);
        }

        return AuthMapper.mapToRegisterRes(createdAppUser);
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
