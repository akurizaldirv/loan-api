package com.enigma.livecodeloan.controller;

import com.enigma.livecodeloan.constant.AppPath;
import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.request.RegisterCustomerRequest;
import com.enigma.livecodeloan.model.response.CommonResponse;
import com.enigma.livecodeloan.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH_BASE)
public class AuthController {

    private final AuthService authService;

    @PostMapping(AppPath.REGISTER)
    public ResponseEntity<?> register(@RequestBody RegisterCustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(authService.registerCustomer(customerRequest))
                                .build()
                );
    }
    @PostMapping(AppPath.ADMIN + AppPath.REGISTER)
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(authService.registerAdmin(request))
                                .build()
                );
    }
    @PostMapping(AppPath.STAFF + AppPath.REGISTER)
    public ResponseEntity<?> registerStaff(@RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(authService.registerStaff(request))
                                .build()
                );
    }


    @PostMapping(AppPath.LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(authService.login(authRequest))
                                .build()
                );
    }
}
