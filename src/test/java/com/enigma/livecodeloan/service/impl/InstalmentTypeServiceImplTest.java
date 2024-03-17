package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.repository.InstalmentTypeRepository;
import com.enigma.livecodeloan.util.enums.EInstalmentType;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.InstalmentTypeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class InstalmentTypeServiceImplTest {
    private InstalmentTypeRepository instalmentTypeRepository;
    private InstalmentTypeServiceImpl instalmentTypeService;

    @BeforeEach
    void setUp() {
        instalmentTypeRepository = mock(InstalmentTypeRepository.class);
        instalmentTypeService = new InstalmentTypeServiceImpl(instalmentTypeRepository);
        mockStatic(InstalmentTypeMapper.class);
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