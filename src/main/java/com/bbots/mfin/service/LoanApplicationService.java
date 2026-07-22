package com.bbots.mfin.service;

import com.bbots.mfin.model.LoanApplication;
import com.bbots.mfin.dto.ResponseDTO;
import java.util.List;

public interface LoanApplicationService {
    List<LoanApplication> getAll();
    LoanApplication getById(Long orgCode, String queueId);
    ResponseDTO<LoanApplication> create(LoanApplication app);
    ResponseDTO<LoanApplication> update(LoanApplication app);
    void delete(Long orgCode, String queueId);
}
