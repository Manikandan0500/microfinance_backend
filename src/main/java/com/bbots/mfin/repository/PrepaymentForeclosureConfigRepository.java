package com.bbots.mfin.repository;
 
import java.sql.Date;

import java.util.List;

import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;
 
import com.bbots.mfin.dto.PrepaymentForeclosureConfigDTO;
 
@Repository

public class PrepaymentForeclosureConfigRepository {
 
    @Autowired

    private JdbcTemplate jdbcTemplate;
 
    private final RowMapper<PrepaymentForeclosureConfigDTO> rowMapper = (rs, rowNum) -> {
 
        PrepaymentForeclosureConfigDTO config = new PrepaymentForeclosureConfigDTO();
 
        config.setOrgcode(rs.getLong("orgcode"));

        config.setProduct_code(rs.getString("product_code"));

        config.setLock_in_period_months(rs.getInt("lock_in_period_months"));

        config.setPrepayment_penalty_type(rs.getString("prepayment_penalty_type"));

        config.setPrepayment_penalty_value(rs.getDouble("prepayment_penalty_value"));

        config.setForeclosure_fee_type(rs.getString("foreclosure_fee_type"));

        config.setForeclosure_fee_value(rs.getDouble("foreclosure_fee_value"));

        config.setSchedule_recalc_method(rs.getString("schedule_recalc_method"));

        config.setConfig_status(rs.getString("config_status"));
 
        config.setEuser(rs.getString("euser"));

        config.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());

        config.setAuser(rs.getString("auser"));

        config.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());

        config.setCuser(rs.getString("cuser"));

        config.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());
 
        return config;

    };
 
    public List<PrepaymentForeclosureConfigDTO> findAll() {
 
        String sql = "SELECT * FROM loandev.LOAN107";
 
        return jdbcTemplate.query(sql, rowMapper);

    }
 
    public List<PrepaymentForeclosureConfigDTO> findByIdOrgCode(Long orgCode) {
 
        String sql = "SELECT * FROM loandev.LOAN107 WHERE orgcode=?";
 
        return jdbcTemplate.query(sql, rowMapper, orgCode);

    }
 
    public Optional<PrepaymentForeclosureConfigDTO> findById(Long orgCode,

                                                             String productCode) {
 
        String sql = "SELECT * FROM loandev.LOAN107 "

                   + "WHERE orgcode=? "

                   + "AND product_code=?";
 
        List<PrepaymentForeclosureConfigDTO> results =

                jdbcTemplate.query(

                        sql,

                        rowMapper,

                        orgCode,

                        productCode);
 
        return results.stream().findFirst();

    }
 
    public boolean existsById(Long orgCode,

                              String productCode) {
 
        String sql = "SELECT COUNT(*) FROM loandev.LOAN107 "

                   + "WHERE orgcode=? "

                   + "AND product_code=?";
 
        Integer count = jdbcTemplate.queryForObject(

                sql,

                Integer.class,

                orgCode,

                productCode);
 
        return count != null && count > 0;

    }
 
    public PrepaymentForeclosureConfigDTO save(PrepaymentForeclosureConfigDTO config) {
 
        if (existsById(config.getOrgcode(), config.getProduct_code())) {
 
            String sql = "UPDATE loandev.LOAN107 SET "

                    + "lock_in_period_months=?, "

                    + "prepayment_penalty_type=?, "

                    + "prepayment_penalty_value=?, "

                    + "foreclosure_fee_type=?, "

                    + "foreclosure_fee_value=?, "

                    + "schedule_recalc_method=?, "

                    + "config_status=?, "

                    + "euser=?, "

                    + "edate=?, "

                    + "auser=?, "

                    + "adate=?, "

                    + "cuser=?, "

                    + "cdate=? "

                    + "WHERE orgcode=? "

                    + "AND product_code=?";
 
            jdbcTemplate.update(

                    sql,

                    config.getLock_in_period_months(),

                    config.getPrepayment_penalty_type(),

                    config.getPrepayment_penalty_value(),

                    config.getForeclosure_fee_type(),

                    config.getForeclosure_fee_value(),

                    config.getSchedule_recalc_method(),

                    config.getConfig_status(),

                    config.getEuser(),

                    config.getEdate() != null ? Date.valueOf(config.getEdate()) : null,

                    config.getAuser(),

                    config.getAdate() != null ? Date.valueOf(config.getAdate()) : null,

                    config.getCuser(),

                    config.getCdate() != null ? Date.valueOf(config.getCdate()) : null,

                    config.getOrgcode(),

                    config.getProduct_code());
 
        } else {
 
            String sql = "INSERT INTO loandev.LOAN107 ("

                    + "orgcode,"

                    + "product_code,"

                    + "lock_in_period_months,"

                    + "prepayment_penalty_type,"

                    + "prepayment_penalty_value,"

                    + "foreclosure_fee_type,"

                    + "foreclosure_fee_value,"

                    + "schedule_recalc_method,"

                    + "config_status,"

                    + "euser,"

                    + "edate,"

                    + "auser,"

                    + "adate,"

                    + "cuser,"

                    + "cdate"

                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 
            jdbcTemplate.update(

                    sql,

                    config.getOrgcode(),

                    config.getProduct_code(),

                    config.getLock_in_period_months(),

                    config.getPrepayment_penalty_type(),

                    config.getPrepayment_penalty_value(),

                    config.getForeclosure_fee_type(),

                    config.getForeclosure_fee_value(),

                    config.getSchedule_recalc_method(),

                    config.getConfig_status(),

                    config.getEuser(),

                    config.getEdate() != null ? Date.valueOf(config.getEdate()) : null,

                    config.getAuser(),

                    config.getAdate() != null ? Date.valueOf(config.getAdate()) : null,

                    config.getCuser(),

                    config.getCdate() != null ? Date.valueOf(config.getCdate()) : null);

        }
 
        return config;

    }
 
}
 