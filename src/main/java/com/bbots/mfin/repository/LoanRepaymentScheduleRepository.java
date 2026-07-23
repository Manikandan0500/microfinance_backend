package com.bbots.mfin.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.LoanRepaymentScheduleDTO;

@Repository
public class LoanRepaymentScheduleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<LoanRepaymentScheduleDTO> rowMapper = new RowMapper<LoanRepaymentScheduleDTO>() {
        @Override
        public LoanRepaymentScheduleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            LoanRepaymentScheduleDTO dto = new LoanRepaymentScheduleDTO();
            dto.setLoanAccountNo(rs.getString("LOAN_ACCOUNT_NO"));
            dto.setInstallmentNo(rs.getObject("INSTALLMENT_NO") != null ? rs.getInt("INSTALLMENT_NO") : null);
            dto.setDueDate(rs.getDate("DUE_DATE") != null ? rs.getDate("DUE_DATE").toString() : rs.getString("DUE_DATE"));
            dto.setPrincipalDue(rs.getObject("PRINCIPAL_DUE") != null ? rs.getDouble("PRINCIPAL_DUE") : null);
            dto.setInterestDue(rs.getObject("INTEREST_DUE") != null ? rs.getDouble("INTEREST_DUE") : null);
            dto.setTotalDue(rs.getObject("TOTAL_DUE") != null ? rs.getDouble("TOTAL_DUE") : null);
            dto.setPrincipalPaid(rs.getObject("PRINCIPAL_PAID") != null ? rs.getDouble("PRINCIPAL_PAID") : null);
            dto.setInterestPaid(rs.getObject("INTEREST_PAID") != null ? rs.getDouble("INTEREST_PAID") : null);
            dto.setInstallmentStatus(rs.getString("INSTALLMENT_STATUS"));
            return dto;
        }
    };

    public List<LoanRepaymentScheduleDTO> findByLoanAccountNo(String loanAccountNo) {
        String sql = "SELECT LOAN_ACCOUNT_NO, INSTALLMENT_NO, DUE_DATE, PRINCIPAL_DUE, INTEREST_DUE, TOTAL_DUE, PRINCIPAL_PAID, INTEREST_PAID, INSTALLMENT_STATUS "
                   + "FROM repay001 WHERE LOAN_ACCOUNT_NO = ? ORDER BY INSTALLMENT_NO ASC";
        return jdbcTemplate.query(sql, rowMapper, loanAccountNo);
    }

    public List<LoanRepaymentScheduleDTO> findAll() {
        String sql = "SELECT LOAN_ACCOUNT_NO, INSTALLMENT_NO, DUE_DATE, PRINCIPAL_DUE, INTEREST_DUE, TOTAL_DUE, PRINCIPAL_PAID, INTEREST_PAID, INSTALLMENT_STATUS "
                   + "FROM repay001 ORDER BY LOAN_ACCOUNT_NO ASC, INSTALLMENT_NO ASC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
