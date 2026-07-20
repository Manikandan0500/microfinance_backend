package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.DelinquencyBucketDTO;

@Repository
public class DelinquencyBucketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<DelinquencyBucketDTO> rowMapper = (rs, rowNum) -> {

        DelinquencyBucketDTO bucket = new DelinquencyBucketDTO();

        bucket.setOrgcode(rs.getLong("orgcode"));
        bucket.setProduct_code(rs.getString("product_code"));
        bucket.setDelinquency_code(rs.getString("delinquency_code"));
        bucket.setBucket_label(rs.getString("bucket_label"));
        bucket.setOverdue_days_from(rs.getInt("overdue_days_from"));
        bucket.setOverdue_days_to(rs.getInt("overdue_days_to"));
        bucket.setStage_order(rs.getInt("stage_order"));
        bucket.setIs_npa_flag(rs.getString("is_npa_flag"));
        bucket.setProvision_pct(rs.getDouble("provision_pct"));
        bucket.setBucket_status(rs.getString("bucket_status"));

        bucket.setEuser(rs.getString("euser"));
        bucket.setEdate(rs.getDate("edate") == null ? null : rs.getDate("edate").toString());
        bucket.setAuser(rs.getString("auser"));
        bucket.setAdate(rs.getDate("adate") == null ? null : rs.getDate("adate").toString());
        bucket.setCuser(rs.getString("cuser"));
        bucket.setCdate(rs.getDate("cdate") == null ? null : rs.getDate("cdate").toString());

        return bucket;
    };

    public List<DelinquencyBucketDTO> findAll() {

        String sql = "SELECT * FROM loandev.LOAN102";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<DelinquencyBucketDTO> findByIdOrgCode(Long orgCode) {

        String sql = "SELECT * FROM loandev.LOAN102 WHERE orgcode=?";

        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<DelinquencyBucketDTO> findById(Long orgCode,
                                                   String productCode,
                                                   String delinquencyCode) {

        String sql = "SELECT * FROM loandev.LOAN102 "
                   + "WHERE orgcode=? "
                   + "AND product_code=? "
                   + "AND delinquency_code=?";

        List<DelinquencyBucketDTO> results =
                jdbcTemplate.query(sql, rowMapper,
                        orgCode,
                        productCode,
                        delinquencyCode);

        return results.stream().findFirst();
    }

    public boolean existsById(Long orgCode,
                              String productCode,
                              String delinquencyCode) {

        String sql = "SELECT COUNT(*) FROM loandev.LOAN102 "
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
    
    public DelinquencyBucketDTO save(DelinquencyBucketDTO bucket) {

        if (existsById(bucket.getOrgcode(),
                       bucket.getProduct_code(),
                       bucket.getDelinquency_code())) {

            String sql = "UPDATE loandev.LOAN102 SET "
                    + "bucket_label = ?, "
                    + "overdue_days_from = ?, "
                    + "overdue_days_to = ?, "
                    + "stage_order = ?, "
                    + "is_npa_flag = ?, "
                    + "provision_pct = ?, "
                    + "bucket_status = ?, "
                    + "euser = ?, "
                    + "edate = ?, "
                    + "auser = ?, "
                    + "adate = ?, "
                    + "cuser = ?, "
                    + "cdate = ? "
                    + "WHERE orgcode = ? "
                    + "AND product_code = ? "
                    + "AND delinquency_code = ?";

            jdbcTemplate.update(
                    sql,
                    bucket.getBucket_label(),
                    bucket.getOverdue_days_from(),
                    bucket.getOverdue_days_to(),
                    bucket.getStage_order(),
                    bucket.getIs_npa_flag(),
                    bucket.getProvision_pct(),
                    bucket.getBucket_status(),
                    bucket.getEuser(),
                    bucket.getEdate() != null ? Date.valueOf(bucket.getEdate()) : null,
                    bucket.getAuser(),
                    bucket.getAdate() != null ? Date.valueOf(bucket.getAdate()) : null,
                    bucket.getCuser(),
                    bucket.getCdate() != null ? Date.valueOf(bucket.getCdate()) : null,
                    bucket.getOrgcode(),
                    bucket.getProduct_code(),
                    bucket.getDelinquency_code());

        } else {

            String sql = "INSERT INTO loandev.LOAN102 ("
                    + "orgcode,"
                    + "product_code,"
                    + "delinquency_code,"
                    + "bucket_label,"
                    + "overdue_days_from,"
                    + "overdue_days_to,"
                    + "stage_order,"
                    + "is_npa_flag,"
                    + "provision_pct,"
                    + "bucket_status,"
                    + "euser,"
                    + "edate,"
                    + "auser,"
                    + "adate,"
                    + "cuser,"
                    + "cdate"
                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(
                    sql,
                    bucket.getOrgcode(),
                    bucket.getProduct_code(),
                    bucket.getDelinquency_code(),
                    bucket.getBucket_label(),
                    bucket.getOverdue_days_from(),
                    bucket.getOverdue_days_to(),
                    bucket.getStage_order(),
                    bucket.getIs_npa_flag(),
                    bucket.getProvision_pct(),
                    bucket.getBucket_status(),
                    bucket.getEuser(),
                    bucket.getEdate() != null ? Date.valueOf(bucket.getEdate()) : null,
                    bucket.getAuser(),
                    bucket.getAdate() != null ? Date.valueOf(bucket.getAdate()) : null,
                    bucket.getCuser(),
                    bucket.getCdate() != null ? Date.valueOf(bucket.getCdate()) : null);
        }

        return bucket;
    }

    public void deleteById(Long orgCode,
                           String productCode,
                           String delinquencyCode) {

        String sql = "DELETE FROM loandev.LOAN102 "
                   + "WHERE orgcode = ? "
                   + "AND product_code = ? "
                   + "AND delinquency_code = ?";

        jdbcTemplate.update(sql,
                orgCode,
                productCode,
                delinquencyCode);
    }

}