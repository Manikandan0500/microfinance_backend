package com.bbots.mfin.serviceImpl;

import com.bbots.mfin.model.LoanApplication;
import com.bbots.mfin.model.LoanApplicationId;
import com.bbots.mfin.repository.LoanApplicationRepository;
import com.bbots.mfin.service.LoanApplicationService;
import com.bbots.mfin.service.AuthorizationProcedureService;
import com.bbots.mfin.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    @Override
    public List<LoanApplication> getAll() {
        return loanApplicationRepository.findAll();
    }

    @Override
    public LoanApplication getById(Long orgCode, String queueId) {
        return loanApplicationRepository.findById(orgCode, queueId);
    }

    @Override
    public ResponseDTO<LoanApplication> create(LoanApplication app) {
        ResponseDTO<LoanApplication> response = new ResponseDTO<>();
        try {
            if (app.getOrgCode() == null) {
                app.setOrgCode(101L);
            }
            app.setEUser(app.getUserName() != null && !app.getUserName().isEmpty() ? app.getUserName() : "SYS");
            app.setEDate(java.time.LocalDateTime.now());
            
            if (app.getSourceRefNo() == null || app.getSourceRefNo().isEmpty()) {
                app.setSourceRefNo(loanApplicationRepository.getNextSourceRefNo());
            }
            
            app.setDisbursementStatus("PENDING");
            
            authProcedureService.processAuthorization(app.getOrgCode(), "LOANAPP", "lnapp001", app, "INSERT");
            
            response.setSuccess(true);
            response.setMessage("Sent for authorization");
            response.setData(app);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO<LoanApplication> update(LoanApplication app) {
        ResponseDTO<LoanApplication> response = new ResponseDTO<>();
        try {
            app.setCUser(app.getUserName() != null && !app.getUserName().isEmpty() ? app.getUserName() : "SYS");
            app.setCDate(java.time.LocalDateTime.now());
            
            authProcedureService.processAuthorization(app.getOrgCode(), "LOANAPP", "lnapp001", app, "UPDATE");
            
            response.setSuccess(true);
            response.setMessage("Sent for authorization");
            response.setData(app);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public void delete(Long orgCode, String queueId) {
        loanApplicationRepository.delete(orgCode, queueId);
    }
}
