package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.response.auth.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUserId(String id);
    UserResponse getById(String id);
    User getUserByCustomerId(String id);
}
