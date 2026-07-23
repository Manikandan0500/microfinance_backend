package com.bbots.mfin.service;

import java.util.List;

import com.bbots.mfin.dto.LoanRepaymentScheduleDTO;
import com.bbots.mfin.dto.ResponseDTO;

public interface LoanRepaymentScheduleService {

    ResponseDTO<List<LoanRepaymentScheduleDTO>> getLoanRepaymentSchedule(String loanAccountNo);

    ResponseDTO<List<LoanRepaymentScheduleDTO>> getAllLoanRepaymentSchedules();
}
