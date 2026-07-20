package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.PenaltyRateHistoryDTO;

@Repository
public class PenaltyRateHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<PenaltyRateHistoryDTO> rowMapper = (rs, rowNum) -> {

        PenaltyRateHistoryDTO penalty = new PenaltyRateHistoryDTO();

        penalty.setOrgcode(rs.getLong("orgcode"));
        penalty.setProduct_code(rs.getString("product_code"));
        penalty.setDelinquency_code(rs.getString("delinquency_code"));
        penalty.setEff_date(rs.getDate("eff_date") == null ? null : rs.getDate("eff_date").toString());
        penalty.setPenalty_type(rs.getString("penalty_type"));
        penalty.setPenalty_value(rs.getDouble("penalty_value"));
        penalty.setRate_status(rs.getString("rate_status"));

        penalty.setEuser(rs.getString("euser"));
        penalty.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());
        penalty.setAuser(rs.getString("auser"));
        penalty.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());
        penalty.setCuser(rs.getString("cuser"));
        penalty.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());

        return penalty;
    };

    public List<PenaltyRateHistoryDTO> findAll() {

        String sql = "SELECT * FROM loandev.LOAN103";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<PenaltyRateHistoryDTO> findByIdOrgCode(Long orgCode) {

        String sql = "SELECT * FROM loandev.LOAN103 WHERE orgcode=?";

        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<PenaltyRateHistoryDTO> findById(Long orgCode,
                                                    String productCode,
                                                    String delinquencyCode,
                                                    String effDate) {

        String sql = "SELECT * FROM loandev.LOAN103 "
                + "WHERE orgcode=? "
                + "AND product_code=? "
                + "AND delinquency_code=? "
                + "AND eff_date=?";

        List<PenaltyRateHistoryDTO> results =
                jdbcTemplate.query(sql,
                        rowMapper,
                        orgCode,
                        productCode,
                        delinquencyCode,
                        Date.valueOf(effDate));

        return results.stream().findFirst();
    }

    public boolean existsById(Long orgCode,
                              String productCode,
                              String delinquencyCode,
                              String effDate) {

        String sql = "SELECT COUNT(*) FROM loandev.LOAN103 "
                + "WHERE orgcode=? "
                + "AND product_code=? "
                + "AND delinquency_code=? "
                + "AND eff_date=?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                orgCode,
                productCode,
                delinquencyCode,
                Date.valueOf(effDate));

        return count != null && count > 0;
    }
    
    public PenaltyRateHistoryDTO save(PenaltyRateHistoryDTO penalty) {

        if (existsById(
                penalty.getOrgcode(),
                penalty.getProduct_code(),
                penalty.getDelinquency_code(),
                penalty.getEff_date())) {

            String sql = "UPDATE loandev.LOAN103 SET "
                    + "penalty_type = ?, "
                    + "penalty_value = ?, "
                    + "rate_status = ?, "
                    + "euser = ?, "
                    + "edate = ?, "
                    + "auser = ?, "
                    + "adate = ?, "
                    + "cuser = ?, "
                    + "cdate = ? "
                    + "WHERE orgcode = ? "
                    + "AND product_code = ? "
                    + "AND delinquency_code = ? "
                    + "AND eff_date = ?";

            jdbcTemplate.update(
                    sql,
                    penalty.getPenalty_type(),
                    penalty.getPenalty_value(),
                    penalty.getRate_status(),
                    penalty.getEuser(),
                    penalty.getEdate() != null ? Date.valueOf(penalty.getEdate()) : null,
                    penalty.getAuser(),
                    penalty.getAdate() != null ? Date.valueOf(penalty.getAdate()) : null,
                    penalty.getCuser(),
                    penalty.getCdate() != null ? Date.valueOf(penalty.getCdate()) : null,
                    penalty.getOrgcode(),
                    penalty.getProduct_code(),
                    penalty.getDelinquency_code(),
                    Date.valueOf(penalty.getEff_date()));

        } else {

            String sql = "INSERT INTO loandev.LOAN103 ("
                    + "orgcode,"
                    + "product_code,"
                    + "delinquency_code,"
                    + "eff_date,"
                    + "penalty_type,"
                    + "penalty_value,"
                    + "rate_status,"
                    + "euser,"
                    + "edate,"
                    + "auser,"
                    + "adate,"
                    + "cuser,"
                    + "cdate"
                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(
                    sql,
                    penalty.getOrgcode(),
                    penalty.getProduct_code(),
                    penalty.getDelinquency_code(),
                    Date.valueOf(penalty.getEff_date()),
                    penalty.getPenalty_type(),
                    penalty.getPenalty_value(),
                    penalty.getRate_status(),
                    penalty.getEuser(),
                    penalty.getEdate() != null ? Date.valueOf(penalty.getEdate()) : null,
                    penalty.getAuser(),
                    penalty.getAdate() != null ? Date.valueOf(penalty.getAdate()) : null,
                    penalty.getCuser(),
                    penalty.getCdate() != null ? Date.valueOf(penalty.getCdate()) : null);
        }

        return penalty;
    }

}