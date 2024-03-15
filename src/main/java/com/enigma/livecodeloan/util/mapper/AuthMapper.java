package com.enigma.livecodeloan.util.mapper;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.UserRole;
import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.response.auth.LoginResponse;
import com.enigma.livecodeloan.model.response.auth.RegisterResponse;

import java.util.ArrayList;
import java.util.List;

public class AuthMapper {
    public static AppUser mapToEntity(AuthRequest authRequest, String encryptedPassword) {
        return AppUser.builder()
                .email(authRequest.getEmail())
                .password(encryptedPassword)
                .build();
    }

    public static LoginResponse mapToLoginRes(AppUser appUser, String token) {
        List<String> roles = new ArrayList<>();
        for (UserRole role : appUser.getRoles()) {
            roles.add(role.getRole().getRole().name());
        }

        return LoginResponse.builder()
                .email(appUser.getEmail())
                .token(token)
                .role(roles)
                .build();
    }

    public static RegisterResponse mapToRegisterRes(AppUser appUser) {
        List<String> roles = new ArrayList<>();
        for (UserRole role : appUser.getRoles()) {
            roles.add(role.getRole().getRole().name());
        }

        return RegisterResponse.builder()
                .email(appUser.getEmail())
                .role(roles)
                .build();
    }
}
