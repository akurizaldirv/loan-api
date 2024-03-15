package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.request.RegisterCustomerRequest;

public interface CustomerService {
    Customer create(AppUser appUser);
    Customer createCustomer(AppUser appUser, RegisterCustomerRequest request);
}
