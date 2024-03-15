package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.InstalmentType;
import com.enigma.livecodeloan.model.request.instalmenttype.InstalmentTypeRequest;
import com.enigma.livecodeloan.model.request.instalmenttype.UpdateInstalmentTypeRequest;
import com.enigma.livecodeloan.model.response.instalmenttype.InstalmentTypeResponse;
import com.enigma.livecodeloan.repository.InstalmentTypeRepository;
import com.enigma.livecodeloan.service.InstalmentTypeService;
import com.enigma.livecodeloan.util.enums.EInstalmentType;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.InstalmentTypeMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstalmentTypeImpl implements InstalmentTypeService {

    private final InstalmentTypeRepository instalmentTypeRepository;

    @Override
    public InstalmentTypeResponse create(InstalmentTypeRequest request) {
        InstalmentType instalmentType = instalmentTypeRepository.save(InstalmentTypeMapper.mapToEntity(request));
        return InstalmentTypeMapper.mapToRes(instalmentType);
    }

    @Override
    public InstalmentType getInstalmentTypeById(String id) {
        return instalmentTypeRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Instalment Type not found")
        );
    }

    @Override
    public InstalmentTypeResponse getById(String id) {
        return InstalmentTypeMapper.mapToRes(this.getInstalmentTypeById(id));
    }

    @Override
    public List<InstalmentTypeResponse> getAll() {
        List<InstalmentType> instalmentTypes = instalmentTypeRepository.findAll();

        return instalmentTypes.stream().map(InstalmentTypeMapper::mapToRes).toList();
    }

    @Override
    public InstalmentTypeResponse update(UpdateInstalmentTypeRequest request) {
        InstalmentType instalmentType = this.getInstalmentTypeById(request.getId());
        try {
            EInstalmentType type = EInstalmentType.valueOf(request.getInstalmentType().toUpperCase());

            instalmentType.setInstalmentType(type);
            return InstalmentTypeMapper.mapToRes(instalmentTypeRepository.save(instalmentType));
        } catch (Exception e) {
            throw new ValidationException("Instalment Type is not valid");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        throwIfIdNotExist(id);
        instalmentTypeRepository.deleteById(id);
    }

    @Override
    public void throwIfIdNotExist(String id) {
        this.getInstalmentTypeById(id);
    }
}
