package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.repository.InstalmentTypeRepository;
import com.enigma.livecodeloan.util.enums.EInstalmentType;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.InstalmentTypeMapper;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InstalmentTypeServiceImplTest {
    private InstalmentTypeRepository instalmentTypeRepository;
    private InstalmentTypeServiceImpl instalmentTypeService;
    private static MockedStatic<InstalmentTypeMapper> mockedStatic;

    @BeforeEach
    void setUp() {
        instalmentTypeRepository = mock(InstalmentTypeRepository.class);
        instalmentTypeService = new InstalmentTypeServiceImpl(instalmentTypeRepository);
    }

    @BeforeAll
    public static void init() {
        mockedStatic = mockStatic(InstalmentTypeMapper.class);
    }

    @AfterAll
    public static void close() {
        mockedStatic.close();
    }


    @Test
    void create_ValidInput_InstalmentTypeResponse() {
        InstalmentTypeRequest dummyReq = InstalmentTypeRequest.builder()
                .instalmentType("ONE_MONTH")
                .build();

        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalment123")
                .instalmentType(EInstalmentType.valueOf(dummyReq.getInstalmentType()))
                .build();

        InstalmentTypeResponse dummyRes = InstalmentTypeResponse.builder()
                .id(dummyInstalmentType.getId())
                .instalmentType(dummyInstalmentType.getInstalmentType().name())
                .build();

        when(instalmentTypeRepository.save(any(InstalmentType.class))).thenReturn(dummyInstalmentType);
        when(InstalmentTypeMapper.mapToEntity(dummyReq)).thenReturn(dummyInstalmentType);
        when(InstalmentTypeMapper.mapToRes(dummyInstalmentType)).thenReturn(dummyRes);

        InstalmentTypeResponse acutalRes = instalmentTypeService.create(dummyReq);
        verify(instalmentTypeRepository, times(1)).save(any(InstalmentType.class));

        Assertions.assertEquals(dummyRes.getInstalmentType(), acutalRes.getInstalmentType());
        Assertions.assertEquals(dummyRes.getId(), acutalRes.getId());
    }

    @Test
    void getInstalmentTypeById_ExistId_InstalmentTypeEntity() {
        String id = "instalment123";

        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalment123")
                .instalmentType(EInstalmentType.NINE_MONTHS)
                .build();

        when(instalmentTypeRepository.findById(id)).thenReturn(Optional.of(dummyInstalmentType));

        InstalmentType acutalRes = instalmentTypeService.getInstalmentTypeById(id);
        verify(instalmentTypeRepository, times(1)).findById(id);

        Assertions.assertEquals(dummyInstalmentType.getInstalmentType(), acutalRes.getInstalmentType());
        Assertions.assertEquals(dummyInstalmentType.getId(), acutalRes.getId());
    }

    @Test
    void getById_ExistId_InstalmentTypeResponse() {
        String id = "instalment123";

        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalment123")
                .instalmentType(EInstalmentType.NINE_MONTHS)
                .build();

        InstalmentTypeResponse dummyRes = InstalmentTypeResponse.builder()
                .id(dummyInstalmentType.getId())
                .instalmentType(dummyInstalmentType.getInstalmentType().name())
                .build();

        when(instalmentTypeRepository.findById(id)).thenReturn(Optional.of(dummyInstalmentType));
        when(InstalmentTypeMapper.mapToRes(dummyInstalmentType)).thenReturn(dummyRes);

        InstalmentTypeResponse actualRes = instalmentTypeService.getById(id);
        verify(instalmentTypeRepository, times(1)).findById(id);

        Assertions.assertEquals(dummyRes.getInstalmentType(), actualRes.getInstalmentType());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void getAll_ExistData_InstalmentTypeResponse() {
        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalment123")
                .instalmentType(EInstalmentType.NINE_MONTHS)
                .build();
        List<InstalmentType> instalmentTypes = List.of(dummyInstalmentType);

        InstalmentTypeResponse dummyRes = InstalmentTypeResponse.builder()
                .id(dummyInstalmentType.getId())
                .instalmentType(dummyInstalmentType.getInstalmentType().name())
                .build();
        List<InstalmentTypeResponse> instalmentTypeResponses = List.of(dummyRes);

        when(instalmentTypeRepository.findAll()).thenReturn(instalmentTypes);
        when(InstalmentTypeMapper.mapToRes(dummyInstalmentType)).thenReturn(dummyRes);

        List<InstalmentTypeResponse> actualRes = instalmentTypeService.getAll();
        verify(instalmentTypeRepository, times(1)).findAll();

        Assertions.assertEquals(instalmentTypeResponses.size(), actualRes.size());
        Assertions.assertEquals(dummyRes.getInstalmentType(), actualRes.get(0).getInstalmentType());
        Assertions.assertEquals(dummyRes.getId(), actualRes.get(0).getId());
    }

    @Test
    void update_ValidInput_InstalmentTypeResponse() {
        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalment123")
                .instalmentType(EInstalmentType.NINE_MONTHS)
                .build();

        UpdateInstalmentTypeRequest dummyReq = UpdateInstalmentTypeRequest.builder()
                .id(dummyInstalmentType.getId())
                .instalmentType(dummyInstalmentType.getInstalmentType().name())
                .build();

        InstalmentTypeResponse dummyRes = InstalmentTypeResponse.builder()
                .id(dummyInstalmentType.getId())
                .instalmentType(dummyInstalmentType.getInstalmentType().name())
                .build();

        when(instalmentTypeRepository.findById(dummyReq.getId())).thenReturn(Optional.of(dummyInstalmentType));
        when(instalmentTypeRepository.save(any(InstalmentType.class))).thenReturn(dummyInstalmentType);
        when(InstalmentTypeMapper.mapToRes(dummyInstalmentType)).thenReturn(dummyRes);

        InstalmentTypeResponse actualRes = instalmentTypeService.update(dummyReq);
        verify(instalmentTypeRepository, times(1)).save(any(InstalmentType.class));

        Assertions.assertEquals(dummyRes.getInstalmentType(), actualRes.getInstalmentType());
        Assertions.assertEquals(dummyRes.getId(), actualRes.getId());
    }

    @Test
    void update_InvalidInput_InstalmentTypeResponse() {
        UpdateInstalmentTypeRequest dummyReq = UpdateInstalmentTypeRequest.builder()
                .id("instalment123")
                .instalmentType("INVALID_INPUT")
                .build();

        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalment123")
                .instalmentType(EInstalmentType.NINE_MONTHS)
                .build();

        when(instalmentTypeRepository.findById(dummyReq.getId())).thenReturn(Optional.of(dummyInstalmentType));

        Assertions.assertThrows(ValidationException.class, () -> instalmentTypeService.update(dummyReq));
        verify(instalmentTypeRepository, times(1)).findById(dummyReq.getId());
        verify(instalmentTypeRepository, never()).save(any(InstalmentType.class));
    }

    @Test
    void delete_ExistId_NoResponse() {
        InstalmentType dummyInstalmentType = InstalmentType.builder()
                .id("instalment123")
                .instalmentType(EInstalmentType.NINE_MONTHS)
                .build();

        when(instalmentTypeRepository.findById(dummyInstalmentType.getId())).thenReturn(Optional.of(dummyInstalmentType));

        instalmentTypeService.delete(dummyInstalmentType.getId());

        verify(instalmentTypeRepository, times(1)).deleteById(dummyInstalmentType.getId());
        verify(instalmentTypeRepository, times(1)).findById(dummyInstalmentType.getId());
    }

    @Test
    void delete_NotExistId_ThrowsDataNotFound() {
        String id = "instalment123";

        when(instalmentTypeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> instalmentTypeService.delete(id));
        verify(instalmentTypeRepository, never()).deleteById(id);
    }

    @Test
    void throwIfIdNotExist() {
        String id = "instalment123";

        when(instalmentTypeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> instalmentTypeService.delete(id));
    }
}