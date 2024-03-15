package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.repository.AppUserRepository;
import com.enigma.livecodeloan.service.UserService;
import com.enigma.livecodeloan.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUserId(String id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        return AppUser.builder()
                .email(appUser.getUsername())
                .id(appUser.getId())
                .password(appUser.getPassword())
                .roles(appUser.getRoles())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUserByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        return AppUser.builder()
                .email(appUser.getUsername())
                .id(appUser.getId())
                .password(appUser.getPassword())
                .roles(appUser.getRoles())
                .build();
    }
}
