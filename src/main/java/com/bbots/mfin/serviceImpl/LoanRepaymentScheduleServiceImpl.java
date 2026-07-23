package com.bbots.mfin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.LoanRepaymentScheduleDTO;
import com.bbots.mfin.dto.ResponseDTO;
import com.bbots.mfin.repository.LoanRepaymentScheduleRepository;
import com.bbots.mfin.service.LoanRepaymentScheduleService;

@Service
public class LoanRepaymentScheduleServiceImpl implements LoanRepaymentScheduleService {

    @Autowired
    private LoanRepaymentScheduleRepository loanRepaymentScheduleRepository;

    @Override
    public ResponseDTO<List<LoanRepaymentScheduleDTO>> getLoanRepaymentSchedule(String loanAccountNo) {
        ResponseDTO<List<LoanRepaymentScheduleDTO>> response = new ResponseDTO<>();
        try {
            List<LoanRepaymentScheduleDTO> list = loanRepaymentScheduleRepository.findByLoanAccountNo(loanAccountNo);
            response.setSuccess(true);
            response.setMessage("Loan repayment schedule fetched successfully");
            response.setData(list);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error fetching loan repayment schedule: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO<List<LoanRepaymentScheduleDTO>> getAllLoanRepaymentSchedules() {
        ResponseDTO<List<LoanRepaymentScheduleDTO>> response = new ResponseDTO<>();
        try {
            List<LoanRepaymentScheduleDTO> list = loanRepaymentScheduleRepository.findAll();
            response.setSuccess(true);
            response.setMessage("All loan repayment schedules fetched successfully");
            response.setData(list);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error fetching loan repayment schedules: " + e.getMessage());
        }
        return response;
    }
}
