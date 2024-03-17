package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.Customer;
import com.enigma.livecodeloan.model.entity.Role;
import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.entity.UserRole;
import com.enigma.livecodeloan.model.request.auth.AuthRequest;
import com.enigma.livecodeloan.model.response.auth.RegisterResponse;
import com.enigma.livecodeloan.repository.CustomerRepository;
import com.enigma.livecodeloan.repository.UserRepository;
import com.enigma.livecodeloan.repository.UserRoleRepository;
import com.enigma.livecodeloan.service.CustomerService;
import com.enigma.livecodeloan.service.RoleService;
import com.enigma.livecodeloan.util.enums.ERole;
import com.enigma.livecodeloan.util.enums.EStatus;
import com.enigma.livecodeloan.util.mapper.AuthMapper;
import com.enigma.livecodeloan.util.mapper.LoanTypeMapper;
import com.enigma.livecodeloan.util.security.JwtUtil;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private UserRoleRepository userRoleRepository;
    private UserRepository userRepository;
    private CustomerServiceImpl customerService;
    private RoleService roleService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private AuthServiceImpl authService;
    private CustomerRepository customerRepository;
    private static MockedStatic<AuthMapper> mockedStatic;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userRoleRepository = mock(UserRoleRepository.class);
        customerService = mock(CustomerServiceImpl.class);
        roleService = mock(RoleServiceImpl.class);
        jwtUtil = mock(JwtUtil.class);
        authenticationManager = mock(AuthenticationManager.class);
        passwordEncoder = mock(PasswordEncoder.class);
        customerRepository = mock(CustomerRepository.class);
        authService = new AuthServiceImpl(userRoleRepository, userRepository, customerService, roleService, jwtUtil, authenticationManager, passwordEncoder);
    }

    @BeforeAll
    public static void init() {
        mockedStatic = mockStatic(AuthMapper.class);
    }

    @AfterAll
    public static void close() {
        mockedStatic.close();
    }

    @Test
    void register() {
        AuthRequest dummyReq = AuthRequest.builder()
                .firstName("Customer")
                .lastName("LastCustomer")
                .roles(List.of("ROLE_ADMIN"))
                .email("customer@mail.id")
                .password("customer")
                .phone("088123123123")
                .build();

        List<ERole> dummyERoles = List.of(ERole.ROLE_ADMIN);
        Role dummyRole = Role.builder()
                .id("role123")
                .role(ERole.ROLE_ADMIN)
                .build();
        List<UserRole> dummyUserRole = List.of(UserRole.builder()
                .id("userrole123")
                .role(dummyRole)
                .build());
        dummyRole.setUserRoles(dummyUserRole);

        User dummyUser = User.builder()
                .email(dummyReq.getEmail())
                .roles(dummyUserRole)
                .password("$2a$10$7H/h6/NBdFkXL4dDjEPUZ.yuwNwjxVxTnJqBpmV1dWSNPUMjmkcsG")
                .id("user123")
                .build();

        Customer dummyCustomer = Customer.builder()
                .user(dummyUser)
                .status(EStatus.ACTIVE)
                .phone(dummyReq.getPhone())
                .lastName(dummyReq.getLastName())
                .firstName(dummyReq.getFirstName())
                .build();

        RegisterResponse dummyRes = RegisterResponse.builder()
                .email(dummyReq.getEmail())
                .role(List.of("ROLE_ADMIN"))
                .build();

        when(roleService.getOrSave(dummyERoles.get(0))).thenReturn(dummyRole);
        when(AuthMapper.mapToEntity(dummyReq, passwordEncoder.encode(dummyReq.getPassword()))).thenReturn(dummyUser);
        when(userRepository.save(any(User.class))).thenReturn(dummyUser);
        when(userRoleRepository.save(dummyUserRole.get(0))).thenReturn(dummyUserRole.get(0));
        when(customerRepository.save(any(Customer.class))).thenReturn(dummyCustomer);
        when(AuthMapper.mapToRegisterRes(any(User.class))).thenReturn(dummyRes);

        RegisterResponse actual = authService.register(dummyReq);
        verify(roleService, times(1)).getOrSave(ERole.ROLE_ADMIN);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRoleRepository, times(1)).save(any(UserRole.class));

        Assertions.assertEquals(dummyRes.getEmail(), actual.getEmail());
        Assertions.assertEquals(dummyRes.getRole(), actual.getRole());
    }

    @Test
    void login() {

    }
}