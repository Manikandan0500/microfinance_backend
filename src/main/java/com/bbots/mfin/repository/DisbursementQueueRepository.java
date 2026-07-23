package com.bbots.mfin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.DisbursementQueueDTO;

@Repository
public class DisbursementQueueRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<DisbursementQueueDTO> rowMapper = (rs, rowNum) -> {

        DisbursementQueueDTO dto = new DisbursementQueueDTO();

        dto.setOrgcode(rs.getLong("orgcode"));
        dto.setQueue_id(rs.getString("queue_id"));
        dto.setSource_system(rs.getString("source_system"));
        dto.setSource_ref_no(rs.getString("source_ref_no"));
        dto.setClient_id(rs.getString("client_id"));
        dto.setGroup_code(rs.getString("group_code"));
        dto.setProduct_code(rs.getString("product_code"));
        dto.setApproved_amount(rs.getDouble("approved_amount"));
        dto.setApproved_tenure_months(rs.getInt("approved_tenure_months"));
        dto.setApproved_interest_rate(rs.getDouble("approved_interest_rate"));

        dto.setQueue_date(
                rs.getDate("queue_date") == null
                        ? null
                        : rs.getDate("queue_date").toString());

        dto.setAssigned_to_user_id(rs.getString("assigned_to_user_id"));
        dto.setDisbursement_status(rs.getString("disbursement_status"));
        dto.setCurrency_code(rs.getString("currency_code"));

        return dto;
    };

    /**
     * Fetch all Pending Disbursement Applications
     */
    public List<DisbursementQueueDTO> getPendingApplications(Long orgCode) {

        String sql = "SELECT "
                + "orgcode,"
                + "queue_id,"
                + "source_system,"
                + "source_ref_no,"
                + "client_id,"
                + "group_code,"
                + "product_code,"
                + "approved_amount,"
                + "approved_tenure_months,"
                + "approved_interest_rate,"
                + "queue_date,"
                + "assigned_to_user_id,"
                + "disbursement_status,"
                + "currency_code "
                + "FROM loandev.lnapp001 "
                + "WHERE orgcode=? "
                + "AND disbursement_status='PENDING' "
                + "ORDER BY queue_date";

        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

}