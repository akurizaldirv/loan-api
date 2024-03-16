package com.enigma.livecodeloan.controller;

import com.enigma.livecodeloan.constant.AppPath;
import com.enigma.livecodeloan.model.request.customer.UpdateCustomerRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.response.CommonResponse;
import com.enigma.livecodeloan.service.CustomerService;
import com.enigma.livecodeloan.service.InstalmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.INSTALMENT)
public class InstalmentTypeController {
    private final InstalmentTypeService instalmentTypeService;

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody InstalmentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(instalmentTypeService.create(request))
                                .build()
                );
    }

    @GetMapping(AppPath.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(instalmentTypeService.getById(id))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(instalmentTypeService.getAll())
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<?> update(@Validated @RequestBody UpdateInstalmentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .data(instalmentTypeService.update(request))
                                .build()
                );
    }

    @DeleteMapping(AppPath.ID)
    public ResponseEntity<?> delete(@PathVariable String id) {
        instalmentTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .message("Success")
                                .build()
                );
    }
}
