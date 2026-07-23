package com.bbots.mfin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.LoanStatusHistoryDTO;

@Repository
public class LoanStatusHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<LoanStatusHistoryDTO> rowMapper = (rs, rowNum) -> {

        LoanStatusHistoryDTO loan = new LoanStatusHistoryDTO();

        loan.setOrgcode(rs.getLong("orgcode"));
        loan.setLoan_account_no(rs.getString("loan_account_no"));
        loan.setStatus_seq_no(rs.getInt("status_seq_no"));
        loan.setStatus_from(rs.getString("status_from"));
        loan.setStatus_to(rs.getString("status_to"));

        loan.setChanged_date(rs.getDate("changed_date") == null ? null : rs.getDate("changed_date").toString());
        loan.setChanged_by(rs.getString("changed_by"));
        loan.setRemarks(rs.getString("remarks"));

        loan.setEuser(rs.getString("euser"));
        loan.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());

        loan.setAuser(rs.getString("auser"));
        loan.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());

        loan.setCuser(rs.getString("cuser"));
        loan.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());

        return loan;
    };

    public List<LoanStatusHistoryDTO> findByLoanAccountNo(String loanAccountNo) {

        String sql = "SELECT * FROM loandev.LOAN002 WHERE loan_account_no = ? ORDER BY status_seq_no";

        return jdbcTemplate.query(sql, rowMapper, loanAccountNo);
    }

}