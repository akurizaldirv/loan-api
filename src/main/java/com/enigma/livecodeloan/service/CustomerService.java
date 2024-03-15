package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.request.customer.UpdateCustomerRequest;
import com.enigma.livecodeloan.model.response.customer.CustomerResponse;

import java.util.List;

public interface CustomerService {
    Customer create(User user, AuthRequest authRequest);
    Customer getCustomerById(String id);
    CustomerResponse getById(String id);
    List<CustomerResponse> getAll();
    CustomerResponse update(UpdateCustomerRequest request);
    void delete(String id);
    void throwIfIdNotExist(String id);

}
