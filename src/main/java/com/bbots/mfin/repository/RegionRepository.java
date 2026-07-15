package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.Region;
import com.bbots.mfin.dto.RegionId;

@Repository
public class RegionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Region> rowMapper = (rs, rowNum) -> {
        RegionId id = new RegionId(
            rs.getLong("orgcode"),
            rs.getString("region_code")
        );
        Region region = new Region();
        region.setId(id);
        region.setRegion_name(rs.getString("region_name"));
        region.setState(rs.getString("state"));
        region.setZone(rs.getString("zone"));
        region.seteUser(rs.getString("euser"));
        region.seteDate(rs.getString("edate"));
        region.setaUser(rs.getString("auser"));
        region.setaDate(rs.getString("adate"));
        region.setcDate( rs.getString("cdate"));
       
        
        return region;
    };

    public List<Region> findAll() {
        String sql = "SELECT * FROM loandev.rgn001";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Region> findByIdOrgCode(Long orgCode) {
        String sql = "SELECT * FROM loandev.rgn001 WHERE orgcode = ?";
        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<Region> findById(RegionId id) {
        String sql = "SELECT * FROM loandev.rgn001 WHERE orgcode = ? AND region_code = ?";
        List<Region> results = jdbcTemplate.query(sql, rowMapper, id.getOrgCode(), id.getRegionCode());
        return results.stream().findFirst();
    }

    public boolean existsById(RegionId id) {
        String sql = "SELECT COUNT(*) FROM loandev.rgn001 WHERE orgcode = ? AND region_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id.getOrgCode(), id.getRegionCode());
        return count != null && count > 0;
    }

    public Region save(Region region) {
        if (existsById(region.getId())) {
            // Update
            String sql = "UPDATE loandev.rgn001 SET region_name = ?, state = ?, zone = ?, cuser = ?, cdate = ? " +
                         "WHERE orgcode = ? AND region_code = ?";
            jdbcTemplate.update(
                sql,
                region.getRegion_name(),
                region.getState(),
                region.getZone(),
                region.getcUser(),
                region.getcDate() != null ? Date.valueOf(region.getcDate()) : null,
                region.getId().getOrgCode(),
                region.getId().getRegionCode()
            );
        } else {
            // Insert
            String sql = "INSERT INTO loandev.rgn001 (orgcode, region_code, region_name, state, zone, euser, edate, auser, adate, cuser, cdate) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                sql,
                region.getId().getOrgCode(),
                region.getId().getRegionCode(),
                region.getRegion_name(),
                region.getState(),
                region.getZone(),
                region.geteUser(),
                region.geteDate() != null ? Date.valueOf(region.geteDate()) : null,
                region.getaUser(),
                region.getaDate() != null ? Date.valueOf(region.getaDate()) : null,
                region.getcUser(),
                region.getcDate() != null ? Date.valueOf(region.getcDate()) : null
            );
        }
        return region;
    }

    public void deleteById(RegionId id) {
        String sql = "DELETE FROM loandev.rgn001 WHERE orgcode = ? AND region_code = ?";
        jdbcTemplate.update(sql, id.getOrgCode(), id.getRegionCode());
    }
}
