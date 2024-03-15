package com.enigma.livecodeloan.util.mapper;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.entity.UserRole;
import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.response.auth.LoginResponse;
import com.enigma.livecodeloan.model.response.auth.RegisterResponse;
import com.enigma.livecodeloan.util.enums.ERole;

import java.util.ArrayList;
import java.util.List;

public class AuthMapper {
    public static User mapToEntity(AuthRequest authRequest, String encryptedPassword) {
        return User.builder()
                .email(authRequest.getEmail())
                .password(encryptedPassword)
                .build();
    }

    public static LoginResponse mapToLoginRes(AppUser user, String token) {
        List<String> roles = new ArrayList<>();
        for (ERole role : user.getRoles()) {
            roles.add(role.name());
        }

        return LoginResponse.builder()
                .email(user.getEmail())
                .token(token)
                .role(roles)
                .build();
    }

    public static RegisterResponse mapToRegisterRes(User user) {
        List<String> roles = new ArrayList<>();
        for (UserRole role : user.getRoles()) {
            roles.add(role.getRole().getRole().name());
        }

        return RegisterResponse.builder()
                .email(user.getEmail())
                .role(roles)
                .build();
    }
}
