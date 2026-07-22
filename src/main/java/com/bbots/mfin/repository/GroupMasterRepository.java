package com.bbots.mfin.repository;

import com.bbots.mfin.model.GroupMaster;
import com.bbots.mfin.model.GroupMasterId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupMasterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<GroupMaster> rowMapper = (rs, rowNum) -> {
        GroupMasterId id = new GroupMasterId(
                rs.getLong("ORGCODE"),
                rs.getString("GROUP_CODE")
        );
        GroupMaster group = new GroupMaster();
        group.setId(id);
        group.setGroupName(rs.getString("GROUP_NAME"));
        group.setBranchCode(rs.getLong("BRANCH_CODE"));
        group.setRegionCode(rs.getString("REGION_CODE"));
        group.setRegionalOfficerId(rs.getString("REGIONAL_OFFICER_ID"));
        group.setSourceSystem(rs.getString("SOURCE_SYSTEM"));
        group.setSourceRefNo(rs.getString("SOURCE_REF_NO"));
        group.setMeetingDay(rs.getString("MEETING_DAY"));
        group.setMeetingFrequency(rs.getString("MEETING_FREQUENCY"));
        group.setGroupStatus(rs.getString("GROUP_STATUS"));
        
        group.setEUser(rs.getString("EUSER"));
        group.setEDate(rs.getDate("EDATE") != null ? rs.getDate("EDATE").toLocalDate().atStartOfDay() : null);
        group.setAUser(rs.getString("AUSER"));
        group.setADate(rs.getDate("ADATE") != null ? rs.getDate("ADATE").toLocalDate().atStartOfDay() : null);
        group.setCUser(rs.getString("CUSER"));
        group.setCDate(rs.getDate("CDATE") != null ? rs.getDate("CDATE").toLocalDate().atStartOfDay() : null);

        return group;
    };

    public List<GroupMaster> findAll() {
        String sql = "SELECT * FROM GROUP001";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<GroupMaster> findByOrgCode(Long orgCode) {
        String sql = "SELECT * FROM GROUP001 WHERE ORGCODE = ?";
        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<GroupMaster> findById(GroupMasterId id) {
        String sql = "SELECT * FROM GROUP001 WHERE ORGCODE = ? AND GROUP_CODE = ?";
        List<GroupMaster> results = jdbcTemplate.query(sql, rowMapper, id.getOrgcode(), id.getGroupCode());
        return results.stream().findFirst();
    }

    public boolean existsById(GroupMasterId id) {
        String sql = "SELECT COUNT(*) FROM GROUP001 WHERE ORGCODE = ? AND GROUP_CODE = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id.getOrgcode(), id.getGroupCode());
        return count != null && count > 0;
    }

    public GroupMaster save(GroupMaster group) {
        if (existsById(group.getId())) {
            // Update
            String sql = "UPDATE GROUP001 SET GROUP_NAME = ?, BRANCH_CODE = ?, REGION_CODE = ?, " +
                    "REGIONAL_OFFICER_ID = ?, SOURCE_SYSTEM = ?, SOURCE_REF_NO = ?, MEETING_DAY = ?, " +
                    "MEETING_FREQUENCY = ?, GROUP_STATUS = ?, CUSER = ?, CDATE = ? " +
                    "WHERE ORGCODE = ? AND GROUP_CODE = ?";
            jdbcTemplate.update(
                    sql,
                    group.getGroupName(),
                    group.getBranchCode(),
                    group.getRegionCode(),
                    group.getRegionalOfficerId(),
                    group.getSourceSystem(),
                    group.getSourceRefNo(),
                    group.getMeetingDay(),
                    group.getMeetingFrequency(),
                    group.getGroupStatus(),
                    group.getCUser(),
                    group.getCDate() != null ? java.sql.Date.valueOf(group.getCDate().toLocalDate()) : null,
                    group.getId().getOrgcode(),
                    group.getId().getGroupCode()
            );
        } else {
            // Insert
            String sql = "INSERT INTO GROUP001 (ORGCODE, GROUP_CODE, GROUP_NAME, BRANCH_CODE, REGION_CODE, " +
                    "REGIONAL_OFFICER_ID, SOURCE_SYSTEM, SOURCE_REF_NO, MEETING_DAY, MEETING_FREQUENCY, " +
                    "GROUP_STATUS, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                    sql,
                    group.getId().getOrgcode(),
                    group.getId().getGroupCode(),
                    group.getGroupName(),
                    group.getBranchCode(),
                    group.getRegionCode(),
                    group.getRegionalOfficerId(),
                    group.getSourceSystem(),
                    group.getSourceRefNo(),
                    group.getMeetingDay(),
                    group.getMeetingFrequency(),
                    group.getGroupStatus(),
                    group.getEUser(),
                    group.getEDate() != null ? java.sql.Date.valueOf(group.getEDate().toLocalDate()) : null,
                    group.getAUser(),
                    group.getADate() != null ? java.sql.Date.valueOf(group.getADate().toLocalDate()) : null,
                    group.getCUser(),
                    group.getCDate() != null ? java.sql.Date.valueOf(group.getCDate().toLocalDate()) : null
            );
        }
        return group;
    }
}
