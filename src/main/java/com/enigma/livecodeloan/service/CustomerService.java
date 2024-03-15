package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.request.RegisterCustomerRequest;
import com.enigma.livecodeloan.model.request.UpdateCustomerRequest;
import com.enigma.livecodeloan.model.response.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer create(AppUser appUser);
    Customer createCustomer(AppUser appUser, RegisterCustomerRequest request);
    Customer getCustomerById(String id);
    CustomerResponse getById(String id);
    List<CustomerResponse> getAll();
    CustomerResponse update(UpdateCustomerRequest request);
    void delete(String id);

}
