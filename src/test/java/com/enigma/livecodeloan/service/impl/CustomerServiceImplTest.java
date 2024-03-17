package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.request.customer.UpdateCustomerRequest;
import com.enigma.livecodeloan.model.request.loantype.LoanTypeRequest;
import com.enigma.livecodeloan.model.request.loantype.UpdateLoanTypeRequest;
import com.enigma.livecodeloan.model.response.customer.CustomerResponse;
import com.enigma.livecodeloan.model.response.loantype.LoanTypeResponse;
import com.enigma.livecodeloan.repository.CustomerRepository;
import com.enigma.livecodeloan.repository.LoanTypeRepository;
import com.enigma.livecodeloan.util.enums.EStatus;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.CustomerMapper;
import com.enigma.livecodeloan.util.mapper.LoanTypeMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerService;
    private static MockedStatic<CustomerMapper> mockedStatic;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @BeforeAll
    public static void init() {
        mockedStatic = mockStatic(CustomerMapper.class);
    }

    @AfterAll
    public static void close() {
        mockedStatic.close();
    }

    @Test
    void create_ValidInput_CustomerResponse() {
        Customer dummyCustomer = Customer.builder()
                .id("customer123")
                .firstName("Customer")
                .lastName("LastCustomer")
                .build();

        CustomerResponse dummyRes = CustomerResponse.builder()
                .id(dummyCustomer.getId())
                .firstName(dummyCustomer.getFirstName())
                .lastName(dummyCustomer.getLastName())
                .build();

        User dummyUser = User.builder()
                .customer(dummyCustomer)
                .id("user123")
                .build();

        AuthRequest dummyReq = AuthRequest.builder()
                .firstName(dummyCustomer.getFirstName())
                .lastName(dummyCustomer.getLastName())
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(dummyCustomer);
        when(CustomerMapper.mapToRes(dummyCustomer)).thenReturn(dummyRes);

        Customer actualRes = customerService.create(dummyUser, dummyReq);
        verify(customerRepository, times(1)).save(any(Customer.class));

        Assertions.assertEquals(dummyRes.getFirstName(), actualRes.getFirstName());
        Assertions.assertEquals(dummyRes.getLastName(), actualRes.getLastName());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void getCustomerById_ExistId_CustomerEntity() {
        Customer dummyCustomer = Customer.builder()
                .id("customer123")
                .firstName("Customer")
                .lastName("LastCustomer")
                .build();

        when(customerRepository.findByIdAndStatus(dummyCustomer.getId(), EStatus.ACTIVE)).thenReturn(Optional.of(dummyCustomer));

        Customer actualRes = customerService.getCustomerById(dummyCustomer.getId());
        verify(customerRepository, times(1)).findByIdAndStatus(dummyCustomer.getId(), EStatus.ACTIVE);

        Assertions.assertEquals(dummyCustomer.getFirstName(), actualRes.getFirstName());
        Assertions.assertEquals(dummyCustomer.getLastName(), actualRes.getLastName());
        Assertions.assertEquals(dummyCustomer.getId(), actualRes.getId());
    }

    @Test
    void getById_ExistId_CustomerResponse() {
        Customer dummyCustomer = Customer.builder()
                .id("customer123")
                .firstName("Customer")
                .lastName("LastCustomer")
                .build();

        CustomerResponse dummyRes = CustomerResponse.builder()
                .id(dummyCustomer.getId())
                .firstName(dummyCustomer.getFirstName())
                .lastName(dummyCustomer.getLastName())
                .build();

        when(customerRepository.findByIdAndStatus(dummyCustomer.getId(), EStatus.ACTIVE)).thenReturn(Optional.of(dummyCustomer));
        when(CustomerMapper.mapToRes(dummyCustomer)).thenReturn(dummyRes);

        CustomerResponse actualRes = customerService.getById(dummyCustomer.getId());
        verify(customerRepository, times(1)).findByIdAndStatus(dummyCustomer.getId(), EStatus.ACTIVE);

        Assertions.assertEquals(dummyRes.getFirstName(), actualRes.getFirstName());
        Assertions.assertEquals(dummyRes.getLastName(), actualRes.getLastName());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void getAll_ExistData_CustomerResponse() {
        Customer dummyCustomer = Customer.builder()
                .id("customer123")
                .firstName("Customer")
                .lastName("LastCustomer")
                .build();
        List<Customer> customers = List.of(dummyCustomer);

        CustomerResponse dummyRes = CustomerResponse.builder()
                .id(dummyCustomer.getId())
                .firstName(dummyCustomer.getFirstName())
                .lastName(dummyCustomer.getLastName())
                .build();
        List<CustomerResponse> loanTypeResponses = List.of(dummyRes);

        when(customerRepository.findAllByStatus(EStatus.ACTIVE)).thenReturn(customers);
        when(CustomerMapper.mapToRes(dummyCustomer)).thenReturn(dummyRes);

        List<CustomerResponse> actualRes = customerService.getAll();
        verify(customerRepository, times(1)).findAllByStatus(EStatus.ACTIVE);

        Assertions.assertEquals(loanTypeResponses.size(), actualRes.size());
        Assertions.assertEquals(dummyCustomer.getFirstName(), actualRes.get(0).getFirstName());
        Assertions.assertEquals(dummyCustomer.getLastName(), actualRes.get(0).getLastName());
        Assertions.assertEquals(dummyRes.getId(), actualRes.get(0).getId());
    }

    @Test
    void update_ValidInput_CustomerResponse() {
        Customer dummyCustomer = Customer.builder()
                .id("customer123")
                .firstName("Customer")
                .lastName("LastCustomer")
                .status(EStatus.ACTIVE)
                .dateOfBirth(Date.from(Instant.now()))

                .build();

        UpdateCustomerRequest dummyReq = UpdateCustomerRequest.builder()
                .id(dummyCustomer.getId())
                .firstName(dummyCustomer.getFirstName())
                .lastName(dummyCustomer.getLastName())
                .status(1)
                .dateOfBirth(Date.from(Instant.now()))
                .build();

        CustomerResponse dummyRes = CustomerResponse.builder()
                .id(dummyCustomer.getId())
                .firstName(dummyCustomer.getFirstName())
                .lastName(dummyCustomer.getLastName())
                .status("ACTIVE")
                .dateOfBirth(Date.from(Instant.now()).toString())
                .build();

        when(customerRepository.findByIdAndStatus(dummyReq.getId(), EStatus.ACTIVE)).thenReturn(Optional.of(dummyCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(dummyCustomer);
        when(CustomerMapper.mapToRes(dummyCustomer)).thenReturn(dummyRes);

        CustomerResponse actualRes = customerService.update(dummyReq);
        verify(customerRepository, times(1)).save(any(Customer.class));

        Assertions.assertEquals(dummyRes.getFirstName(), actualRes.getFirstName());
        Assertions.assertEquals(dummyRes.getLastName(), actualRes.getLastName());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void delete_ExistId_NoResponse() {
        Customer dummyCustomer = Customer.builder()
                .id("customer123")
                .firstName("Customer")
                .lastName("LastCustomer")
                .build();

        when(customerRepository.findByIdAndStatus(dummyCustomer.getId(), EStatus.ACTIVE)).thenReturn(Optional.of(dummyCustomer));

        customerService.delete(dummyCustomer.getId());

        verify(customerRepository, times(1)).updateStatus(dummyCustomer.getId(), EStatus.INACTIVE);
        verify(customerRepository, times(1)).findByIdAndStatus(dummyCustomer.getId(), EStatus.ACTIVE);
    }

    @Test
    void delete_NotExistId_ThrowsDataNotFound() {
        String id = "customer123";

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> customerService.delete(id));
        verify(customerRepository, never()).deleteById(id);
    }

    @Test
    void throwIfIdNotExist() {
        String id = "customer123";

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> customerService.delete(id));
    }
}