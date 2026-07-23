package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.DisbursementDTO;
import com.bbots.mfin.dto.RepaymentScheduleDTO;

@Repository
public class DisbursementRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * Get Next Disbursement Sequence Number
     */
    public Integer getNextDisbursementSeqNo(Long orgCode,
                                            String loanAccountNo) {

        String sql = "SELECT COALESCE(MAX(disbursement_seq_no),0)+1 "
                   + "FROM loandev.disb001 "
                   + "WHERE orgcode=? "
                   + "AND loan_account_no=?";

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                orgCode,
                loanAccountNo);
    }

    /**
     * Insert into DISB001
     */
    public void insertDisbursement(DisbursementDTO dto) {

        String sql = "INSERT INTO loandev.disb001 ("
                + "orgcode,"
                + "loan_account_no,"
                + "disbursement_seq_no,"
                + "disbursement_amount,"
                + "currency_code,"
                + "disbursement_mode,"
                + "bank_ref_no,"
                + "disbursed_by_user_id,"
                + "disbursement_date,"
                + "disbursement_status,"
                + "acc_posting_ref,"
                + "acc_posting_status,"
                + "euser,"
                + "edate"
                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(
                sql,
                dto.getOrgcode(),
                dto.getLoan_account_no(),
                dto.getDisbursement_seq_no(),
                dto.getDisbursement_amount(),
                dto.getCurrency_code(),
                dto.getDisbursement_mode(),
                dto.getBank_ref_no(),
                dto.getDisbursed_by_user_id(),
                Date.valueOf(dto.getDisbursement_date()),
                dto.getDisbursement_status(),
                dto.getAcc_posting_ref(),
                dto.getAcc_posting_status(),
                dto.getEuser(),
                Date.valueOf(dto.getEdate()));
    }

    /**
     * Insert into REPAY001
     */
    public void insertRepaymentSchedule(Long orgCode,
                                        String loanAccountNo,
                                        List<RepaymentScheduleDTO> scheduleList,
                                        String euser,
                                        String edate) {

        String sql = "INSERT INTO loandev.repay001 ("
                + "orgcode,"
                + "loan_account_no,"
                + "installment_no,"
                + "due_date,"
                + "principal_due,"
                + "interest_due,"
                + "total_due,"
                + "principal_paid,"
                + "interest_paid,"
                + "installment_status,"
                + "euser,"
                + "edate"
                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        for (RepaymentScheduleDTO schedule : scheduleList) {

            jdbcTemplate.update(
                    sql,
                    orgCode,
                    loanAccountNo,
                    schedule.getInstallment_no(),
                    Date.valueOf(schedule.getDue_date()),
                    schedule.getPrincipal_due(),
                    schedule.getInterest_due(),
                    schedule.getTotal_due(),
                    schedule.getPrincipal_paid(),
                    schedule.getInterest_paid(),
                    schedule.getInstallment_status(),
                    euser,
                    Date.valueOf(edate));
        }
    }

    /**
     * Update LNAPP001
     */
    public void updateLnappStatus(Long orgCode, String loanAccountNo) {

        String sql = "UPDATE loandev.lnapp001 "
                + "SET disbursement_status='COMPLETED' "
                + "WHERE orgcode=? "
                + "AND source_ref_no=?";

        jdbcTemplate.update(
                sql,
                orgCode,
                loanAccountNo);
    }

    /**
     * Update LOAN001
     */
    public void updateLoan001(DisbursementDTO dto) {

        String sql = "UPDATE loandev.loan001 SET "
                + "disbursed_amount=?,"
                + "disbursement_date=?,"
                + "outstanding_principal=?,"
                + "outstanding_interest=?,"
                + "current_delinquency_code='STANDARD' "
                + "WHERE orgcode=? "
                + "AND loan_account_no=?";

        jdbcTemplate.update(
                sql,
                dto.getDisbursement_amount(),
                Date.valueOf(dto.getDisbursement_date()),
                dto.getPrincipal_outstanding(),
                dto.getInterest_outstanding(),
                dto.getOrgcode(),
                dto.getLoan_account_no());
    }

    /**
     * Update LOAN003
     */
    public void updateLoan003(DisbursementDTO dto) {

        String sql = "UPDATE loandev.loan003 SET "
                + "principal_outstanding=?,"
                + "interest_outstanding=?,"
                + "total_outstanding=? "
                + "WHERE orgcode=? "
                + "AND loan_account_no=?";

        jdbcTemplate.update(
                sql,
                dto.getPrincipal_outstanding(),
                dto.getInterest_outstanding(),
                dto.getTotal_outstanding(),
                dto.getOrgcode(),
                dto.getLoan_account_no());
    }

}