package com.enigma.livecodeloan.controller;

import com.enigma.livecodeloan.constant.AppPath;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.loan.LoanTypeRequest;
import com.enigma.livecodeloan.model.request.loan.UpdateLoanTypeRequest;
import com.enigma.livecodeloan.model.response.CommonResponse;
import com.enigma.livecodeloan.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(AppPath.LOAN_TYPE)
public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody LoanTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(loanTypeService.create(request))
                                .build()
                );
    }

    @GetMapping(AppPath.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(loanTypeService.getById(id))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(loanTypeService.getAll())
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<?> update(@Validated @RequestBody UpdateLoanTypeRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(loanTypeService.update(request))
                                .build()
                );
    }

    @DeleteMapping(AppPath.ID)
    public ResponseEntity<?> delete(@PathVariable String id) {
        loanTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .build()
                );
    }
}