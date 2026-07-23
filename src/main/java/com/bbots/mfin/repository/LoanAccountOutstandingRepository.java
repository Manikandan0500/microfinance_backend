package com.bbots.mfin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.LoanAccountOutstandingDTO;

@Repository
public class LoanAccountOutstandingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<LoanAccountOutstandingDTO> rowMapper = (rs, rowNum) -> {

        LoanAccountOutstandingDTO loan = new LoanAccountOutstandingDTO();

        loan.setOrgcode(rs.getLong("orgcode"));
        loan.setLoan_account_no(rs.getString("loan_account_no"));
        loan.setAs_on_date(rs.getDate("as_on_date") == null ? null : rs.getDate("as_on_date").toString());

        loan.setPrincipal_outstanding(rs.getDouble("principal_outstanding"));
        loan.setInterest_outstanding(rs.getDouble("interest_outstanding"));
        loan.setPenalty_outstanding(rs.getDouble("penalty_outstanding"));
        loan.setTotal_outstanding(rs.getDouble("total_outstanding"));

        loan.setEuser(rs.getString("euser"));
        loan.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());

        loan.setAuser(rs.getString("auser"));
        loan.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());

        loan.setCuser(rs.getString("cuser"));
        loan.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());

        return loan;
    };

    public List<LoanAccountOutstandingDTO> findByLoanAccountNo(String loanAccountNo) {

        String sql = "SELECT * FROM loandev.LOAN003 WHERE loan_account_no = ?";

        return jdbcTemplate.query(sql, rowMapper, loanAccountNo);
    }

}