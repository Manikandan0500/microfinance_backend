package com.bbots.mfin.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.BranchRegionMap;
import com.bbots.mfin.dto.BranchRegionMapId;

@Repository
public class BranchRegionMapRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BranchRegionMap> findAllByOrgCode(Long orgcode) {
        String sql = "SELECT * FROM rgn002 WHERE orgcode = ?";
        return jdbcTemplate.query(sql, new RowMapper<BranchRegionMap>() {
            @Override
            public BranchRegionMap mapRow(ResultSet rs, int rowNum) throws SQLException {
                BranchRegionMap map = new BranchRegionMap();
                BranchRegionMapId id = new BranchRegionMapId();
                id.setOrgcode(rs.getLong("orgcode"));
                id.setBranch_code(rs.getLong("branch_code"));
                map.setId(id);
                map.setOrgcode(rs.getLong("orgcode"));
                map.setBranch_code(rs.getLong("branch_code"));
                map.setRegion_code(rs.getString("region_code"));
                map.seteUser(rs.getString("euser"));
                map.seteDate(rs.getString("edate"));
                map.setaUser(rs.getString("auser"));
                map.setaDate(rs.getString("adate"));
                map.setcUser(rs.getString("cuser"));
                map.setcDate(rs.getString("cdate"));
                return map;
            }
        }, orgcode);
    }

    public boolean existsById(BranchRegionMapId id) {
        String sql = "SELECT count(*) FROM rgn002 WHERE orgcode = ? AND branch_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id.getOrgcode(), id.getBranch_code());
        return count != null && count > 0;
    }
}
