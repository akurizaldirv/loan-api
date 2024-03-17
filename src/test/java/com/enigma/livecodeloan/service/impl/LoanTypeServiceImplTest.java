package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.loantype.LoanTypeRequest;
import com.enigma.livecodeloan.model.request.loantype.UpdateLoanTypeRequest;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.model.response.loantype.LoanTypeResponse;
import com.enigma.livecodeloan.repository.InstalmentTypeRepository;
import com.enigma.livecodeloan.repository.LoanTypeRepository;
import com.enigma.livecodeloan.util.enums.EInstalmentType;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.InstalmentTypeMapper;
import com.enigma.livecodeloan.util.mapper.LoanTypeMapper;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class LoanTypeServiceImplTest {
    private LoanTypeRepository loanTypeRepository;
    private LoanTypeServiceImpl loanTypeService;
    private static MockedStatic<LoanTypeMapper> mockedStatic;

    @BeforeEach
    void setUp() {
        loanTypeRepository = mock(LoanTypeRepository.class);
        loanTypeService = new LoanTypeServiceImpl(loanTypeRepository);
    }

    @BeforeAll
    public static void init() {
        mockedStatic = mockStatic(LoanTypeMapper.class);
    }

    @AfterAll
    public static void close() {
        mockedStatic.close();
    }


    @Test
    void create_ValidInput_InstalmentTypeResponse() {
        LoanType dummyLoanType = LoanType.builder()
                .maxLoan(20000000.0)
                .type("Pinjaman Pribadi")
                .id("loantype123")
                .build();

        LoanTypeRequest dummyReq = LoanTypeRequest.builder()
                .maxLoan(dummyLoanType.getMaxLoan().longValue())
                .type(dummyLoanType.getType())
                .build();

        LoanTypeResponse dummyRes = LoanTypeResponse.builder()
                .id(dummyLoanType.getId())
                .type(dummyLoanType.getType())
                .maxLoan(dummyLoanType.getMaxLoan().longValue())
                .build();

        when(loanTypeRepository.save(any(LoanType.class))).thenReturn(dummyLoanType);
        when(LoanTypeMapper.mapToEntity(dummyReq)).thenReturn(dummyLoanType);
        when(LoanTypeMapper.mapToRes(dummyLoanType)).thenReturn(dummyRes);

        LoanTypeResponse actualRes = loanTypeService.create(dummyReq);
        verify(loanTypeRepository, times(1)).save(any(LoanType.class));

        Assertions.assertEquals(dummyRes.getMaxLoan(), actualRes.getMaxLoan());
        Assertions.assertEquals(dummyRes.getType(), actualRes.getType());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void getLoanTypeById_ExistId_LoanTypeEntity() {
        String id = "instalment123";

        LoanType dummyLoanType = LoanType.builder()
                .maxLoan(20000000.0)
                .type("Pinjaman Pribadi")
                .id("loantype123")
                .build();

        when(loanTypeRepository.findById(id)).thenReturn(Optional.of(dummyLoanType));

        LoanType actualRes = loanTypeService.getLoanTypeById(id);
        verify(loanTypeRepository, times(1)).findById(id);

        Assertions.assertEquals(dummyLoanType.getType(), actualRes.getType());
        Assertions.assertEquals(dummyLoanType.getId(), actualRes.getId());
    }

    @Test
    void getById_ExistId_LoanTypeResponse() {
        String id = "instalment123";

        LoanType dummyLoanType = LoanType.builder()
                .maxLoan(20000000.0)
                .type("Pinjaman Pribadi")
                .id("loantype123")
                .build();

        LoanTypeResponse dummyRes = LoanTypeResponse.builder()
                .id(dummyLoanType.getId())
                .type(dummyLoanType.getType())
                .maxLoan(dummyLoanType.getMaxLoan().longValue())
                .build();

        when(loanTypeRepository.findById(id)).thenReturn(Optional.of(dummyLoanType));
        when(LoanTypeMapper.mapToRes(dummyLoanType)).thenReturn(dummyRes);

        LoanTypeResponse actualRes = loanTypeService.getById(id);
        verify(loanTypeRepository, times(1)).findById(id);

        Assertions.assertEquals(dummyRes.getType(), actualRes.getType());
        Assertions.assertEquals(dummyRes.getMaxLoan(), actualRes.getMaxLoan());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void getAll_ExistData_LoanTypeResponse() {
        LoanType dummyLoanType = LoanType.builder()
                .maxLoan(20000000.0)
                .type("Pinjaman Pribadi")
                .id("loantype123")
                .build();
        List<LoanType> loanTypes = List.of(dummyLoanType);

        LoanTypeResponse dummyRes = LoanTypeResponse.builder()
                .id(dummyLoanType.getId())
                .type(dummyLoanType.getType())
                .maxLoan(dummyLoanType.getMaxLoan().longValue())
                .build();
        List<LoanTypeResponse> loanTypeResponses = List.of(dummyRes);

        when(loanTypeRepository.findAll()).thenReturn(loanTypes);
        when(LoanTypeMapper.mapToRes(dummyLoanType)).thenReturn(dummyRes);

        List<LoanTypeResponse> actualRes = loanTypeService.getAll();
        verify(loanTypeRepository, times(1)).findAll();

        Assertions.assertEquals(loanTypeResponses.size(), actualRes.size());
        Assertions.assertEquals(dummyRes.getType(), actualRes.get(0).getType());
        Assertions.assertEquals(dummyRes.getMaxLoan(), actualRes.get(0).getMaxLoan());
        Assertions.assertEquals(dummyRes.getId(), actualRes.get(0).getId());
    }

    @Test
    void update_ValidInput_LoanTypeResponse() {
        LoanType dummyLoanType = LoanType.builder()
                .maxLoan(20000000.0)
                .type("Pinjaman Pribadi")
                .id("loantype123")
                .build();

        UpdateLoanTypeRequest dummyReq = UpdateLoanTypeRequest.builder()
                .maxLoan(dummyLoanType.getMaxLoan().longValue())
                .type(dummyLoanType.getType())
                .id("loantype123")
                .build();

        LoanTypeResponse dummyRes = LoanTypeResponse.builder()
                .id(dummyLoanType.getId())
                .type(dummyLoanType.getType())
                .maxLoan(dummyLoanType.getMaxLoan().longValue())
                .build();

        when(loanTypeRepository.findById(dummyReq.getId())).thenReturn(Optional.of(dummyLoanType));
        when(loanTypeRepository.save(any(LoanType.class))).thenReturn(dummyLoanType);
        when(LoanTypeMapper.mapToRes(dummyLoanType)).thenReturn(dummyRes);

        LoanTypeResponse actualRes = loanTypeService.update(dummyReq);
        verify(loanTypeRepository, times(1)).save(any(LoanType.class));

        Assertions.assertEquals(dummyRes.getMaxLoan(), actualRes.getMaxLoan());
        Assertions.assertEquals(dummyRes.getType(), actualRes.getType());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void delete_ExistId_NoResponse() {
        LoanType dummyLoanType = LoanType.builder()
                .maxLoan(20000000.0)
                .type("Pinjaman Pribadi")
                .id("loantype123")
                .build();

        when(loanTypeRepository.findById(dummyLoanType.getId())).thenReturn(Optional.of(dummyLoanType));

        loanTypeService.delete(dummyLoanType.getId());

        verify(loanTypeRepository, times(1)).deleteById(dummyLoanType.getId());
        verify(loanTypeRepository, times(1)).findById(dummyLoanType.getId());
    }

    @Test
    void delete_NotExistId_ThrowsDataNotFound() {
        String id = "loantype123";

        when(loanTypeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> loanTypeService.delete(id));
        verify(loanTypeRepository, never()).deleteById(id);
    }

    @Test
    void throwIfIdNotExist() {
        String id = "loantype123";

        when(loanTypeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> loanTypeService.delete(id));
    }
}