package com.enigma.livecodeloan.util.mapper;

import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.response.customer.CustomerResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CustomerMapper {
    public static CustomerResponse mapToRes(Customer customer) {
        System.out.println(customer.getPhone());
        System.out.println(customer.getDateOfBirth());
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(customer.getDateOfBirth());

        return CustomerResponse.builder()
                .id(customer.getId())
                .dateOfBirth(date)
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .status((customer.getStatus()) ? "ACTIVE" : "NON-ACTIVE")
                .build();
    }
}
