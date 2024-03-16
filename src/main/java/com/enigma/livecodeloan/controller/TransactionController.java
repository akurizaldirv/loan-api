package com.enigma.livecodeloan.controller;

import com.enigma.livecodeloan.constant.AppPath;
import com.enigma.livecodeloan.model.request.loantype.LoanTypeRequest;
import com.enigma.livecodeloan.model.request.transaction.ApproveRequest;
import com.enigma.livecodeloan.model.request.transaction.PayRequest;
import com.enigma.livecodeloan.model.request.transaction.RejectRequest;
import com.enigma.livecodeloan.model.request.transaction.TransactionRequest;
import com.enigma.livecodeloan.model.response.CommonResponse;
import com.enigma.livecodeloan.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> create(@Validated @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(transactionService.create(request))
                                .build()
                );
    }

    @GetMapping(AppPath.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(transactionService.getById(id))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(transactionService.getAll())
                                .build()
                );
    }

    @PutMapping(AppPath.ID + AppPath.APPROVE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<?> approve(@PathVariable String id, @Validated @RequestBody ApproveRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(transactionService.approve(id, request))
                                .build()
                );
    }
    @PutMapping(AppPath.ID + AppPath.REJECT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<?> reject(@PathVariable String id, @Validated @RequestBody RejectRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(transactionService.reject(id, request))
                                .build()
                );
    }
    @PutMapping(AppPath.ID + AppPath.PAY)
    public ResponseEntity<?> pay(@PathVariable String id, @Validated @RequestBody PayRequest payRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(transactionService.pay(id, payRequest))
                                .build()
                );
    }
}
