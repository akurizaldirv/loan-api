package com.enigma.livecodeloan.controller;

import com.enigma.livecodeloan.constant.AppPath;
import com.enigma.livecodeloan.model.request.UpdateCustomerRequest;
import com.enigma.livecodeloan.model.response.CommonResponse;
import com.enigma.livecodeloan.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(AppPath.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(customerService.getById(id))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(customerService.getAll())
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<?> update(@Validated @RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(customerService.update(request))
                                .build()
                );
    }

    @DeleteMapping(AppPath.ID)
    public ResponseEntity<?> delete(@PathVariable String id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data("Deleted")
                                .build()
                );
    }
}
