package com.bbots.mfin.repository;

import com.bbots.mfin.model.CifMaster;
import com.bbots.mfin.model.CifMasterId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CifMasterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CifMaster> rowMapper = (rs, rowNum) -> {
        CifMasterId id = new CifMasterId(
                rs.getLong("orgcode"),
                rs.getLong("cifid")
        );
        CifMaster cif = new CifMaster();
        cif.setId(id);
        cif.setCusttype(rs.getString("custtype"));
        cif.setTitle(rs.getString("title"));
        cif.setFirstname(rs.getString("firstname"));
        cif.setMiddlename(rs.getString("middlename"));
        return cif;
    };

    public List<CifMaster> findAll() {
        String sql = "SELECT orgcode, cifid, custtype, title, firstname, middlename FROM cif001";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<CifMaster> findByOrgCode(Long orgCode) {
        String sql = "SELECT orgcode, cifid, custtype, title, firstname, middlename FROM cif001 WHERE orgcode = ?";
        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }
}
