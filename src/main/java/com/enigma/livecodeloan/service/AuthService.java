package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.request.RegisterCustomerRequest;
import com.enigma.livecodeloan.model.response.LoginResponse;
import com.enigma.livecodeloan.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(RegisterCustomerRequest request);
    RegisterResponse registerAdmin(AuthRequest authRequest);
    RegisterResponse registerStaff(AuthRequest authRequest);
    LoginResponse login(AuthRequest authRequest);
}
