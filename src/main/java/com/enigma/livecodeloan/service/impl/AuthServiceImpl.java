package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.response.LoginResponse;
import com.enigma.livecodeloan.model.response.RegisterResponse;
import com.enigma.livecodeloan.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse register(AuthRequest authRequest) {
//        Role role = roleService.getOrSave(ERole.ROLE_ADMIN);
//        UserCredential userCredential = AuthMapper.mapToEntity(authRequest, passwordEncoder.encode(authRequest.getPassword()));
//        userCredential.setRole(role);
//        Admin admin = adminService.create(authRequest, userCredential);
//
//        return AuthMapper.mapToRegisterRes(userCredential);
        return null;
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authRequest.getUsername().toLowerCase(),
//                        authRequest.getPassword()
//                ));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        AppUser appUser = (AppUser) authentication.getPrincipal();
//        String token = jwtUtil.generateToken(appUser);
//
//        return LoginResponse.builder()
//                .token(token)
//                .role(appUser.getRole().name())
//                .build();
        return null;
    }
}
