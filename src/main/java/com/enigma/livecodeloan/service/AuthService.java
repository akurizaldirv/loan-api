package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.response.LoginResponse;
import com.enigma.livecodeloan.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest authRequest);
    LoginResponse login(AuthRequest authRequest);
}
