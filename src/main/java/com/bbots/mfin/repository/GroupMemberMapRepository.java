package com.bbots.mfin.repository;

import com.bbots.mfin.model.GroupMemberMap;
import com.bbots.mfin.model.GroupMemberMapId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupMemberMapRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<GroupMemberMap> rowMapper = (rs, rowNum) -> {
        GroupMemberMapId id = new GroupMemberMapId(
                rs.getLong("ORGCODE"),
                rs.getString("GROUP_CODE"),
                rs.getString("CLIENT_ID")
        );
        GroupMemberMap map = new GroupMemberMap();
        map.setId(id);
        map.setMemberRole(rs.getString("MEMBER_ROLE"));
        map.setJoinDate(rs.getDate("JOIN_DATE") != null ? rs.getDate("JOIN_DATE").toLocalDate().atStartOfDay() : null);
        map.setMemberStatus(rs.getString("MEMBER_STATUS"));
        
        map.setEUser(rs.getString("EUSER"));
        map.setEDate(rs.getDate("EDATE") != null ? rs.getDate("EDATE").toLocalDate().atStartOfDay() : null);
        map.setAUser(rs.getString("AUSER"));
        map.setADate(rs.getDate("ADATE") != null ? rs.getDate("ADATE").toLocalDate().atStartOfDay() : null);
        map.setCUser(rs.getString("CUSER"));
        map.setCDate(rs.getDate("CDATE") != null ? rs.getDate("CDATE").toLocalDate().atStartOfDay() : null);

        return map;
    };

    public List<GroupMemberMap> findAll() {
        String sql = "SELECT * FROM GROUP002";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<GroupMemberMap> findByOrgCodeAndGroupCode(Long orgCode, String groupCode) {
        String sql = "SELECT * FROM GROUP002 WHERE ORGCODE = ? AND GROUP_CODE = ?";
        return jdbcTemplate.query(sql, rowMapper, orgCode, groupCode);
    }

    public Optional<GroupMemberMap> findById(GroupMemberMapId id) {
        String sql = "SELECT * FROM GROUP002 WHERE ORGCODE = ? AND GROUP_CODE = ? AND CLIENT_ID = ?";
        List<GroupMemberMap> results = jdbcTemplate.query(sql, rowMapper, id.getOrgcode(), id.getGroupCode(), id.getClientId());
        return results.stream().findFirst();
    }

    public boolean existsById(GroupMemberMapId id) {
        String sql = "SELECT COUNT(*) FROM GROUP002 WHERE ORGCODE = ? AND GROUP_CODE = ? AND CLIENT_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id.getOrgcode(), id.getGroupCode(), id.getClientId());
        return count != null && count > 0;
    }

    public GroupMemberMap save(GroupMemberMap map) {
        if (existsById(map.getId())) {
            // Update
            String sql = "UPDATE GROUP002 SET MEMBER_ROLE = ?, JOIN_DATE = ?, MEMBER_STATUS = ?, " +
                    "CUSER = ?, CDATE = ? " +
                    "WHERE ORGCODE = ? AND GROUP_CODE = ? AND CLIENT_ID = ?";
            jdbcTemplate.update(
                    sql,
                    map.getMemberRole(),
                    map.getJoinDate() != null ? java.sql.Date.valueOf(map.getJoinDate().toLocalDate()) : null,
                    map.getMemberStatus(),
                    map.getCUser(),
                    map.getCDate() != null ? java.sql.Date.valueOf(map.getCDate().toLocalDate()) : null,
                    map.getId().getOrgcode(),
                    map.getId().getGroupCode(),
                    map.getId().getClientId()
            );
        } else {
            // Insert
            String sql = "INSERT INTO GROUP002 (ORGCODE, GROUP_CODE, CLIENT_ID, MEMBER_ROLE, JOIN_DATE, " +
                    "MEMBER_STATUS, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                    sql,
                    map.getId().getOrgcode(),
                    map.getId().getGroupCode(),
                    map.getId().getClientId(),
                    map.getMemberRole(),
                    map.getJoinDate() != null ? java.sql.Date.valueOf(map.getJoinDate().toLocalDate()) : null,
                    map.getMemberStatus(),
                    map.getEUser(),
                    map.getEDate() != null ? java.sql.Date.valueOf(map.getEDate().toLocalDate()) : null,
                    map.getAUser(),
                    map.getADate() != null ? java.sql.Date.valueOf(map.getADate().toLocalDate()) : null,
                    map.getCUser(),
                    map.getCDate() != null ? java.sql.Date.valueOf(map.getCDate().toLocalDate()) : null
            );
        }
        return map;
    }
}
