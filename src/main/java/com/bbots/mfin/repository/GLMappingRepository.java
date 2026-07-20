package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.GLMappingDTO;

@Repository
public class GLMappingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<GLMappingDTO> rowMapper = (rs, rowNum) -> {

        GLMappingDTO map = new GLMappingDTO();

        map.setOrgcode(rs.getLong("orgcode"));
        map.setProduct_code(rs.getString("product_code"));
        map.setDelinquency_code(rs.getString("delinquency_code"));
        map.setPrin_gl(rs.getString("prin_gl"));
        map.setInt_gl(rs.getString("int_gl"));
        map.setProvision_gl(rs.getString("provision_gl"));
        map.setMap_status(rs.getString("map_status"));

        map.setEuser(rs.getString("euser"));
        map.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());
        map.setAuser(rs.getString("auser"));
        map.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());
        map.setCuser(rs.getString("cuser"));
        map.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());

        return map;
    };

    public List<GLMappingDTO> findAll() {

        String sql = "SELECT * FROM loandev.LOAN104";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<GLMappingDTO> findByIdOrgCode(Long orgCode) {

        String sql = "SELECT * FROM loandev.LOAN104 WHERE orgcode=?";

        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<GLMappingDTO> findById(Long orgCode,
                                           String productCode,
                                           String delinquencyCode) {

        String sql = "SELECT * FROM loandev.LOAN104 "
                   + "WHERE orgcode=? "
                   + "AND product_code=? "
                   + "AND delinquency_code=?";

        List<GLMappingDTO> results =
                jdbcTemplate.query(sql,
                        rowMapper,
                        orgCode,
                        productCode,
                        delinquencyCode);

        return results.stream().findFirst();
    }

    public boolean existsById(Long orgCode,
                              String productCode,
                              String delinquencyCode) {

        String sql = "SELECT COUNT(*) FROM loandev.LOAN104 "
                   + "WHERE orgcode=? "
                   + "AND product_code=? "
                   + "AND delinquency_code=?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                orgCode,
                productCode,
                delinquencyCode);

        return count != null && count > 0;
    }
    
    public GLMappingDTO save(GLMappingDTO map) {

        if (existsById(
                map.getOrgcode(),
                map.getProduct_code(),
                map.getDelinquency_code())) {

            String sql = "UPDATE loandev.LOAN104 SET "
                    + "prin_gl=?, "
                    + "int_gl=?, "
                    + "provision_gl=?, "
                    + "map_status=?, "
                    + "euser=?, "
                    + "edate=?, "
                    + "auser=?, "
                    + "adate=?, "
                    + "cuser=?, "
                    + "cdate=? "
                    + "WHERE orgcode=? "
                    + "AND product_code=? "
                    + "AND delinquency_code=?";

            jdbcTemplate.update(
                    sql,
                    map.getPrin_gl(),
                    map.getInt_gl(),
                    map.getProvision_gl(),
                    map.getMap_status(),
                    map.getEuser(),
                    map.getEdate() != null ? Date.valueOf(map.getEdate()) : null,
                    map.getAuser(),
                    map.getAdate() != null ? Date.valueOf(map.getAdate()) : null,
                    map.getCuser(),
                    map.getCdate() != null ? Date.valueOf(map.getCdate()) : null,
                    map.getOrgcode(),
                    map.getProduct_code(),
                    map.getDelinquency_code());

        } else {

            String sql = "INSERT INTO loandev.LOAN104 ("
                    + "orgcode,"
                    + "product_code,"
                    + "delinquency_code,"
                    + "prin_gl,"
                    + "int_gl,"
                    + "provision_gl,"
                    + "map_status,"
                    + "euser,"
                    + "edate,"
                    + "auser,"
                    + "adate,"
                    + "cuser,"
                    + "cdate"
                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(
                    sql,
                    map.getOrgcode(),
                    map.getProduct_code(),
                    map.getDelinquency_code(),
                    map.getPrin_gl(),
                    map.getInt_gl(),
                    map.getProvision_gl(),
                    map.getMap_status(),
                    map.getEuser(),
                    map.getEdate() != null ? Date.valueOf(map.getEdate()) : null,
                    map.getAuser(),
                    map.getAdate() != null ? Date.valueOf(map.getAdate()) : null,
                    map.getCuser(),
                    map.getCdate() != null ? Date.valueOf(map.getCdate()) : null);
        }

        return map;
    }

}