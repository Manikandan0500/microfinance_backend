package com.bbots.mfin.repository;

import com.bbots.mfin.model.LoanApplication;
import com.bbots.mfin.model.LoanApplicationId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class LoanApplicationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<LoanApplication> rowMapper = new RowMapper<LoanApplication>() {
        @Override
        public LoanApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            LoanApplication app = new LoanApplication();
            app.setOrgCode(rs.getLong("orgcode"));
            app.setQueueId(rs.getString("queue_id"));
            app.setSourceSystem(rs.getString("source_system"));
            app.setSourceRefNo(rs.getString("source_ref_no"));
            app.setCurrencyCode(rs.getString("currency_code"));
            app.setClientId(rs.getString("client_id"));
            app.setGroupCode(rs.getString("group_code"));
            app.setProductCode(rs.getString("product_code"));
            app.setApprovedAmount(rs.getDouble("approved_amount"));
            app.setApprovedTenureMonths(rs.getInt("approved_tenure_months"));
            app.setApprovedInterestRate(rs.getDouble("approved_interest_rate"));
            app.setQueueDate(rs.getDate("queue_date") != null ? rs.getDate("queue_date").toLocalDate() : null);
            app.setAssignedToUserId(rs.getString("assigned_to_user_id"));
            app.setDisbursementStatus(rs.getString("disbursement_status"));
            
            app.setEUser(rs.getString("euser"));
            app.setEDate(rs.getDate("edate") != null ? rs.getDate("edate").toLocalDate().atStartOfDay() : null);
            app.setAUser(rs.getString("auser"));
            app.setADate(rs.getDate("adate") != null ? rs.getDate("adate").toLocalDate().atStartOfDay() : null);
            app.setCUser(rs.getString("cuser"));
            app.setCDate(rs.getDate("cdate") != null ? rs.getDate("cdate").toLocalDate().atStartOfDay() : null);
            return app;
        }
    };

    public List<LoanApplication> findAll() {
        String sql = "SELECT * FROM loandev.lnapp001";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public LoanApplication findById(Long orgCode, String queueId) {
        String sql = "SELECT * FROM loandev.lnapp001 WHERE orgcode = ? AND queue_id = ?";
        List<LoanApplication> list = jdbcTemplate.query(sql, rowMapper, orgCode, queueId);
        return list.isEmpty() ? null : list.get(0);
    }

    public String getNextSourceRefNo() {
        String sql = "SELECT 'LN' || LPAD(nextval('loandev.loan_account_seq')::TEXT, 14, '0')";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public void save(LoanApplication app) {
        String sql = "INSERT INTO loandev.lnapp001 (orgcode, queue_id, source_system, source_ref_no, client_id, group_code, " +
                "product_code, approved_amount, approved_tenure_months, approved_interest_rate, queue_date, " +
                "assigned_to_user_id, disbursement_status, currency_code, euser, edate, auser, adate, cuser, cdate) " +
                "VALUES (?, ?, ?, 'LN' || LPAD(nextval('loandev.loan_account_seq')::TEXT, 14, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                app.getOrgCode(),
                app.getQueueId(),
                app.getSourceSystem(),
                app.getClientId(),
                app.getGroupCode(),
                app.getProductCode(),
                app.getApprovedAmount(),
                app.getApprovedTenureMonths(),
                app.getApprovedInterestRate(),
                app.getQueueDate(),
                app.getAssignedToUserId(),
                app.getDisbursementStatus(),
                app.getCurrencyCode(),
                app.getEUser(),
                app.getEDate() != null ? Timestamp.valueOf(app.getEDate()) : null,
                app.getAUser(),
                app.getADate() != null ? Timestamp.valueOf(app.getADate()) : null,
                app.getCUser(),
                app.getCDate() != null ? Timestamp.valueOf(app.getCDate()) : null
        );
    }

    public void update(LoanApplication app) {
        String sql = "UPDATE loandev.lnapp001 SET source_system=?, client_id=?, group_code=?, product_code=?, " +
                "approved_amount=?, approved_tenure_months=?, approved_interest_rate=?, queue_date=?, assigned_to_user_id=?, " +
                "disbursement_status=?, currency_code=?, auser=?, adate=?, cuser=?, cdate=? " +
                "WHERE orgcode=? AND queue_id=?";
        jdbcTemplate.update(sql,
                app.getSourceSystem(),
                app.getClientId(),
                app.getGroupCode(),
                app.getProductCode(),
                app.getApprovedAmount(),
                app.getApprovedTenureMonths(),
                app.getApprovedInterestRate(),
                app.getQueueDate(),
                app.getAssignedToUserId(),
                app.getDisbursementStatus(),
                app.getCurrencyCode(),
                app.getAUser(),
                app.getADate() != null ? Timestamp.valueOf(app.getADate()) : null,
                app.getCUser(),
                app.getCDate() != null ? Timestamp.valueOf(app.getCDate()) : null,
                app.getOrgCode(),
                app.getQueueId()
        );
    }

    public void delete(Long orgCode, String queueId) {
        String sql = "DELETE FROM loandev.lnapp001 WHERE orgcode = ? AND queue_id = ?";
        jdbcTemplate.update(sql, orgCode, queueId);
    }
}
