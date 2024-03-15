package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.request.AuthRequest;
import com.enigma.livecodeloan.model.request.RegisterCustomerRequest;
import com.enigma.livecodeloan.repository.CustomerRepository;
import com.enigma.livecodeloan.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(AppUser appUser) {
        Customer customer = new Customer();
        customer.setUser(appUser);

        return customerRepository.save(customer);
    }

    @Override
    public Customer createCustomer(AppUser appUser, RegisterCustomerRequest request) {
        Customer customer = Customer.builder()
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(true)
                .user(appUser)
                .build();

        return null;
    }
}
