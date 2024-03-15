package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.response.auth.LoginResponse;
import com.enigma.livecodeloan.model.response.auth.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    LoginResponse login(AuthRequest authRequest);
}
