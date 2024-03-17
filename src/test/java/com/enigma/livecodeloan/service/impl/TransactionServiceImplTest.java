package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.*;
import com.enigma.livecodeloan.model.request.transaction.IdRequest;
import com.enigma.livecodeloan.model.request.transaction.TransactionRequest;
import com.enigma.livecodeloan.model.response.customer.CustomerResponse;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.model.response.loantype.LoanTypeResponse;
import com.enigma.livecodeloan.model.response.transaction.TransactionResponse;
import com.enigma.livecodeloan.repository.LoanTransactionDetailRepository;
import com.enigma.livecodeloan.repository.LoanTransactionRepository;
import com.enigma.livecodeloan.util.enums.LoanStatus;
import com.enigma.livecodeloan.util.mapper.TransactionMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.mockito.Mockito.*;

class TransactionServiceImplTest {
    private LoanTransactionRepository loanTransactionRepository;
    private LoanTransactionDetailRepository loanTransactionDetailRepository;
    private InstalmentTypeServiceImpl instalmentTypeService;
    private CustomerServiceImpl customerService;
    private UserServiceImpl userService;
    private LoanTypeServiceImpl loanTypeService;
    private TransactionServiceImpl transactionService;
    private static MockedStatic<TransactionMapper> mockedStatic;

    @BeforeEach
    void setUp() {
        loanTransactionRepository = mock(LoanTransactionRepository.class);
        loanTransactionDetailRepository = mock(LoanTransactionDetailRepository.class);
        instalmentTypeService = mock(InstalmentTypeServiceImpl.class);
        customerService = mock(CustomerServiceImpl.class);
        userService = mock(UserServiceImpl.class);
        loanTypeService = mock(LoanTypeServiceImpl.class);
        transactionService = new TransactionServiceImpl(
                loanTransactionRepository,
                loanTransactionDetailRepository,
                instalmentTypeService,
                customerService,
                userService,
                loanTypeService
        );
    }

    @BeforeAll
    public static void init() {
        mockedStatic = mockStatic(TransactionMapper.class);
    }

    @AfterAll
    public static void close() {
        mockedStatic.close();
    }
    @Test
    void create_ValidInput_ReturnTransactionResponse() {
        TransactionRequest dummyReq = TransactionRequest.builder()
                .customer(IdRequest.builder().id("customer123").build())
                .instalmentType(IdRequest.builder().id("instalmentType123").build())
                .loanType(IdRequest.builder().id("loanType123").build())
                .nominal(2000000L)
                .build();

        Customer dummyCustomer = Customer.builder()
                .id("customer123")
                .build();
        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalmentType123")
                .build();
        LoanType dummyLoanType = LoanType.builder()
                .id("loanType123")
                .maxLoan(100000000.0)
                .build();

        LoanTransaction dummyLoanTransaction = LoanTransaction.builder()
                .id("loanTrx123")
                .customer(dummyCustomer)
                .loanType(dummyLoanType)
                .instalmentType(dummyInstalmentType)
                .nominal(dummyReq.getNominal().doubleValue())
                .build();

        TransactionResponse dummyRes = TransactionResponse.builder()
                .id(dummyLoanTransaction.getId())
                .customer(CustomerResponse.builder()
                        .id(dummyCustomer.getId())
                        .build())
                .loanType(LoanTypeResponse.builder()
                        .id(dummyLoanType.getId())
                        .maxLoan(dummyLoanType.getMaxLoan().longValue())
                        .build())
                .instalmentType(InstalmentTypeResponse.builder()
                        .id(dummyInstalmentType.getId())
                        .build())
                .nominal(dummyLoanTransaction.getNominal())
                .build();

        when(customerService.getCustomerById(dummyReq.getCustomer().getId())).thenReturn(dummyCustomer);
        when(instalmentTypeService.getInstalmentTypeById(dummyReq.getInstalmentType().getId())).thenReturn(dummyInstalmentType);
        when(loanTypeService.getLoanTypeById(dummyReq.getLoanType().getId())).thenReturn(dummyLoanType);
        when(TransactionMapper.mapToTrxEntity(dummyReq, dummyCustomer, dummyInstalmentType, dummyLoanType))
                .thenReturn(dummyLoanTransaction);
        when(loanTransactionRepository.save(any(LoanTransaction.class))).thenReturn(dummyLoanTransaction);
        when(TransactionMapper.mapToTrxRes(any(LoanTransaction.class), eq(null))).thenReturn(dummyRes);
        TransactionResponse actual = transactionService.create(dummyReq);

        Assertions.assertNotEquals(null, actual);
        Assertions.assertEquals(dummyCustomer.getId(), actual.getCustomer().getId());
        Assertions.assertEquals(dummyLoanType.getId(), actual.getLoanType().getId());
        Assertions.assertEquals(dummyInstalmentType.getId(), actual.getInstalmentType().getId());
        Assertions.assertEquals(dummyReq.getNominal(), dummyRes.getNominal().longValue());
    }

    @Test
    void getTrxById_ExistId_ReturnLoanTransaction() {
        LoanTransaction dummyLoan = LoanTransaction.builder()
                .id("loan123")
                .nominal(20000000.0)
                .build();

        when(loanTransactionRepository.findById(dummyLoan.getId())).thenReturn(Optional.of(dummyLoan));

        LoanTransaction actual = transactionService.getTrxById(dummyLoan.getId());
        verify(loanTransactionRepository, times(1)).findById(dummyLoan.getId());

        Assertions.assertEquals(dummyLoan.getId(), actual.getId());
        Assertions.assertEquals(dummyLoan.getNominal(), actual.getNominal());
    }

    @Test
    void getTrxDetailById_ExistId_ReturnLoanTransactionDetail() {
        LoanTransaction dummyLoan = LoanTransaction.builder()
                .id("loan123")
                .nominal(20000000.0)
                .build();
        LoanTransactionDetail dummyLoanDetail = LoanTransactionDetail.builder()
                .loanTransaction(dummyLoan)
                .loanStatus(LoanStatus.UNPAID)
                .nominal(20000.0)
                .id("ld123")
                .build();

        when(loanTransactionDetailRepository.findById(dummyLoanDetail.getId())).thenReturn(Optional.of(dummyLoanDetail));

        LoanTransactionDetail actual = transactionService.getTrxDetailById(dummyLoanDetail.getId());
        verify(loanTransactionDetailRepository, times(1)).findById(dummyLoanDetail.getId());

        Assertions.assertEquals(dummyLoanDetail.getId(), actual.getId());
        Assertions.assertEquals(dummyLoanDetail.getNominal(), actual.getNominal());
    }

    @Test
    void getTrxDetailByIdAndLoanId() {
        LoanTransaction dummyLoan = LoanTransaction.builder()
                .id("loan123")
                .nominal(20000000.0)
                .build();
        LoanTransactionDetail dummyLoanDetail = LoanTransactionDetail.builder()
                .loanTransaction(dummyLoan)
                .loanStatus(LoanStatus.UNPAID)
                .nominal(20000.0)
                .id("ld123")
                .build();

        when(loanTransactionDetailRepository.findByIdAndLoanId(dummyLoanDetail.getId(), dummyLoan.getId())).thenReturn(Optional.of(dummyLoanDetail));

        LoanTransactionDetail actual = transactionService.getTrxDetailByIdAndLoanId(dummyLoanDetail.getId(), dummyLoan.getId());
        verify(loanTransactionDetailRepository, times(1)).findByIdAndLoanId(dummyLoanDetail.getId(), dummyLoan.getId());

        Assertions.assertEquals(dummyLoanDetail.getId(), actual.getId());
        Assertions.assertEquals(dummyLoanDetail.getNominal(), actual.getNominal());
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void approve() {
    }

    @Test
    void reject() {
    }

    @Test
    void pay() {
    }
}