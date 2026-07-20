package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.RateRevisionHistoryDTO;

@Repository
public class RateRevisionHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<RateRevisionHistoryDTO> rowMapper = (rs, rowNum) -> {

        RateRevisionHistoryDTO rate = new RateRevisionHistoryDTO();

        rate.setOrgcode(rs.getLong("orgcode"));
        rate.setProduct_code(rs.getString("product_code"));
        rate.setEff_date(rs.getDate("eff_date") == null ? null : rs.getDate("eff_date").toString());
        rate.setRevised_rate(rs.getDouble("revised_rate"));
        rate.setBenchmark_rate_code(rs.getString("benchmark_rate_code"));
        rate.setSpread_pct(rs.getDouble("spread_pct"));
        rate.setRevision_reason(rs.getString("revision_reason"));
        rate.setRevision_status(rs.getString("revision_status"));

        rate.setEuser(rs.getString("euser"));
        rate.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());
        rate.setAuser(rs.getString("auser"));
        rate.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());
        rate.setCuser(rs.getString("cuser"));
        rate.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());

        return rate;
    };

    public List<RateRevisionHistoryDTO> findAll() {

        String sql = "SELECT * FROM loandev.LOAN108";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<RateRevisionHistoryDTO> findByIdOrgCode(Long orgCode) {

        String sql = "SELECT * FROM loandev.LOAN108 WHERE orgcode=?";

        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<RateRevisionHistoryDTO> findById(Long orgCode,
                                                     String productCode,
                                                     String effDate) {

        String sql = "SELECT * FROM loandev.LOAN108 "
                   + "WHERE orgcode=? "
                   + "AND product_code=? "
                   + "AND eff_date=?";

        List<RateRevisionHistoryDTO> results =
                jdbcTemplate.query(sql,
                        rowMapper,
                        orgCode,
                        productCode,
                        Date.valueOf(effDate));

        return results.stream().findFirst();
    }

    public boolean existsById(Long orgCode,
                              String productCode,
                              String effDate) {

        String sql = "SELECT COUNT(*) FROM loandev.LOAN108 "
                   + "WHERE orgcode=? "
                   + "AND product_code=? "
                   + "AND eff_date=?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                orgCode,
                productCode,
                Date.valueOf(effDate));

        return count != null && count > 0;
    }
    
    public RateRevisionHistoryDTO save(RateRevisionHistoryDTO rate) {

        if (existsById(
                rate.getOrgcode(),
                rate.getProduct_code(),
                rate.getEff_date())) {

            String sql = "UPDATE loandev.LOAN108 SET "
                    + "revised_rate=?, "
                    + "benchmark_rate_code=?, "
                    + "spread_pct=?, "
                    + "revision_reason=?, "
                    + "revision_status=?, "
                    + "euser=?, "
                    + "edate=?, "
                    + "auser=?, "
                    + "adate=?, "
                    + "cuser=?, "
                    + "cdate=? "
                    + "WHERE orgcode=? "
                    + "AND product_code=? "
                    + "AND eff_date=?";

            jdbcTemplate.update(
                    sql,
                    rate.getRevised_rate(),
                    rate.getBenchmark_rate_code(),
                    rate.getSpread_pct(),
                    rate.getRevision_reason(),
                    rate.getRevision_status(),
                    rate.getEuser(),
                    rate.getEdate() != null ? Date.valueOf(rate.getEdate()) : null,
                    rate.getAuser(),
                    rate.getAdate() != null ? Date.valueOf(rate.getAdate()) : null,
                    rate.getCuser(),
                    rate.getCdate() != null ? Date.valueOf(rate.getCdate()) : null,
                    rate.getOrgcode(),
                    rate.getProduct_code(),
                    Date.valueOf(rate.getEff_date()));

        } else {

            String sql = "INSERT INTO loandev.LOAN108 ("
                    + "orgcode,"
                    + "product_code,"
                    + "eff_date,"
                    + "revised_rate,"
                    + "benchmark_rate_code,"
                    + "spread_pct,"
                    + "revision_reason,"
                    + "revision_status,"
                    + "euser,"
                    + "edate,"
                    + "auser,"
                    + "adate,"
                    + "cuser,"
                    + "cdate"
                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(
                    sql,
                    rate.getOrgcode(),
                    rate.getProduct_code(),
                    Date.valueOf(rate.getEff_date()),
                    rate.getRevised_rate(),
                    rate.getBenchmark_rate_code(),
                    rate.getSpread_pct(),
                    rate.getRevision_reason(),
                    rate.getRevision_status(),
                    rate.getEuser(),
                    rate.getEdate() != null ? Date.valueOf(rate.getEdate()) : null,
                    rate.getAuser(),
                    rate.getAdate() != null ? Date.valueOf(rate.getAdate()) : null,
                    rate.getCuser(),
                    rate.getCdate() != null ? Date.valueOf(rate.getCdate()) : null);
        }

        return rate;
    }

}