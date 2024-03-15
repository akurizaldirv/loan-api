package com.enigma.livecodeloan.service;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.request.AuthRequest;

public interface CustomerService {
    Customer create(AppUser appUser);
}
