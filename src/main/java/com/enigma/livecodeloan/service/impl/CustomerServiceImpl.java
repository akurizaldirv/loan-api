package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.request.auth.RegisterCustomerRequest;
import com.enigma.livecodeloan.model.request.customer.UpdateCustomerRequest;
import com.enigma.livecodeloan.model.response.customer.CustomerResponse;
import com.enigma.livecodeloan.repository.CustomerRepository;
import com.enigma.livecodeloan.service.CustomerService;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(User user) {
        Customer customer = new Customer();
        customer.setUser(user);

        return customerRepository.save(customer);
    }

    @Override
    public Customer createCustomer(User user, RegisterCustomerRequest request) {
        Customer customer = Customer.builder()
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(true)
                .user(user)
                .build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findByIdAndStatusTrue(id).orElseThrow(
                () -> new DataNotFoundException("Customer not found")
        );
    }

    @Override
    public CustomerResponse getById(String id) {
        return CustomerMapper.mapToRes(this.getCustomerById(id));
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAllByStatusTrue();

        return customers.stream().map(CustomerMapper::mapToRes).toList();
    }

    @Override
    public CustomerResponse update(UpdateCustomerRequest request) {
        Customer customer = this.getCustomerById(request.getId());

        customer.setPhone(request.getPhone());
        customer.setStatus(request.getStatus() == 1);
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDateOfBirth(request.getDateOfBirth());

        return CustomerMapper.mapToRes(customerRepository.save(customer));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        this.throwIfIdNotExist(id);
        customerRepository.softDeleteById(id);
    }

    @Override
    public void throwIfIdNotExist(String id) {
        this.getCustomerById(id);
    }
}
