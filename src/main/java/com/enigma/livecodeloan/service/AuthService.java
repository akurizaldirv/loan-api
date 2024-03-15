package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.request.auth.RegisterCustomerRequest;
import com.enigma.livecodeloan.model.response.auth.LoginResponse;
import com.enigma.livecodeloan.model.response.auth.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(RegisterCustomerRequest request);
    RegisterResponse registerAdmin(AuthRequest authRequest);
    RegisterResponse registerStaff(AuthRequest authRequest);
    LoginResponse login(AuthRequest authRequest);
}
