package com.enigma.livecodeloan.service.impl;

import com.enigma.livecodeloan.model.entity.LoanType;
import com.enigma.livecodeloan.model.request.loantype.LoanTypeRequest;
import com.enigma.livecodeloan.model.request.loantype.UpdateLoanTypeRequest;
import com.enigma.livecodeloan.model.response.loantype.LoanTypeResponse;
import com.enigma.livecodeloan.repository.LoanTypeRepository;
import com.enigma.livecodeloan.service.LoanTypeService;
import com.enigma.livecodeloan.util.exception.DataNotFoundException;
import com.enigma.livecodeloan.util.mapper.LoanTypeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;


    @Override
    public LoanTypeResponse create(LoanTypeRequest request) {
        LoanType loanType = loanTypeRepository.save(LoanTypeMapper.mapToEntity(request));
        return LoanTypeMapper.mapToRes(loanType);
    }

    @Override
    public LoanType getLoanTypeById(String id) {
        return loanTypeRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Loan Type not found")
        );
    }

    @Override
    public LoanTypeResponse getById(String id) {
        return LoanTypeMapper.mapToRes(this.getLoanTypeById(id));
    }

    @Override
    public List<LoanTypeResponse> getAll() {
        List<LoanType> loanTypes = loanTypeRepository.findAll();

        return loanTypes.stream().map(LoanTypeMapper::mapToRes).toList();
    }

    @Override
    public LoanTypeResponse update(UpdateLoanTypeRequest request) {
        LoanType loanType = this.getLoanTypeById(request.getId());
        loanType.setType(request.getType());
        loanType.setMaxLoan(request.getMaxLoan().doubleValue());

        return LoanTypeMapper.mapToRes(loanTypeRepository.save(loanType));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        throwIfIdNotExist(id);
        loanTypeRepository.deleteById(id);
    }

    @Override
    public void throwIfIdNotExist(String id) {
        this.getLoanTypeById(id);
    }
}
