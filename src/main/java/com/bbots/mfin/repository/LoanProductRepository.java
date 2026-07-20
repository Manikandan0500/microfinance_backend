package com.bbots.mfin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.LoanProductDTO;


@Repository
public class LoanProductRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    private final RowMapper<LoanProductDTO> rowMapper = (rs, rowNum) -> {
 
    	LoanProductDTO loan = new LoanProductDTO();

    	loan.setOrgcode(rs.getLong("orgcode"));
    	loan.setProduct_code(rs.getString("product_code"));
    	loan.setProduct_name(rs.getString("product_name"));
    	loan.setMin_amount(rs.getInt("min_amount"));
    	loan.setMax_amount(rs.getInt("max_amount"));
    	loan.setInterest_rate(rs.getInt("interest_rate"));
    	loan.setInterest_type(rs.getString("interest_type"));
    	loan.setRate_type(rs.getString("rate_type"));
    	loan.setBenchmark_rate_code(rs.getString("benchmark_rate_code"));
    	loan.setMin_tenure_months(rs.getInt("min_tenure_months"));
    	loan.setMax_tenure_months(rs.getInt("max_tenure_months"));
    	loan.setRepay_frequency(rs.getString("repay_frequency"));
    	loan.setPrin_gl(rs.getString("prin_gl"));
    	loan.setInt_gl(rs.getString("int_gl"));
    	loan.setPenal_gl(rs.getString("penal_gl"));
    	loan.setProduct_status(rs.getString("product_status"));

    	loan.setEuser(rs.getString("euser"));
    	loan.setEdate(rs.getString("edate"));
    	loan.setAuser(rs.getString("auser"));
    	loan.setAdate(rs.getString("adate"));
    	loan.setCuser(rs.getString("cuser"));
    	loan.setCdate(rs.getString("cdate"));
       
        
        return loan;
    };

    public List<LoanProductDTO> findAll() {
        String sql = "SELECT * FROM loandev.LOAN101";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<LoanProductDTO> findByIdOrgCode(Long orgCode) {
        String sql = "SELECT * FROM loandev.LOAN101 WHERE orgcode = ?";
        return jdbcTemplate.query(sql, rowMapper, orgCode);
    }

    public Optional<LoanProductDTO> findById(Long orgCode, String productCode) {

        String sql = "SELECT * FROM loandev.LOAN101 WHERE orgcode = ? AND product_code = ?";

        List<LoanProductDTO> results =
                jdbcTemplate.query(sql, rowMapper, orgCode, productCode);

        return results.stream().findFirst();
    }
    
    public boolean existsById(Long orgCode, String productCode) {

        String sql = "SELECT COUNT(*) FROM loandev.LOAN101 WHERE orgcode = ? AND product_code = ?";

        Integer count =
                jdbcTemplate.queryForObject(sql, Integer.class, orgCode, productCode);

        return count != null && count > 0;
    }

    public LoanProductDTO save(LoanProductDTO loan) {

        if (existsById(loan.getOrgcode(), loan.getProduct_code())) {

            String sql = "UPDATE loandev.LOAN101 SET "
                    + "product_name = ?, "
                    + "min_amount = ?, "
                    + "max_amount = ?, "
                    + "interest_rate = ?, "
                    + "interest_type = ?, "
                    + "rate_type = ?, "
                    + "benchmark_rate_code = ?, "
                    + "min_tenure_months = ?, "
                    + "max_tenure_months = ?, "
                    + "repay_frequency = ?, "
                    + "prin_gl = ?, "
                    + "int_gl = ?, "
                    + "penal_gl = ?, "
                    + "product_status = ?, "
                    + "euser = ?, "
                    + "edate = ?, "
                    + "auser = ?, "
                    + "adate = ?, "
                    + "cuser = ?, "
                    + "cdate = ? "
                    + "WHERE orgcode = ? AND product_code = ?";

            jdbcTemplate.update(
                    sql,
                    loan.getProduct_name(),
                    loan.getMin_amount(),
                    loan.getMax_amount(),
                    loan.getInterest_rate(),
                    loan.getInterest_type(),
                    loan.getRate_type(),
                    loan.getBenchmark_rate_code(),
                    loan.getMin_tenure_months(),
                    loan.getMax_tenure_months(),
                    loan.getRepay_frequency(),
                    loan.getPrin_gl(),
                    loan.getInt_gl(),
                    loan.getPenal_gl(),
                    loan.getProduct_status(),
                    loan.getEuser(),
                    loan.getEdate() != null ? Date.valueOf(loan.getEdate()) : null,
                    loan.getAuser(),
                    loan.getAdate() != null ? Date.valueOf(loan.getAdate()) : null,
                    loan.getCuser(),
                    loan.getCdate() != null ? Date.valueOf(loan.getCdate()) : null,
                    loan.getOrgcode(),
                    loan.getProduct_code());

        } else {

            String sql = "INSERT INTO loandev.LOAN101 ("
                    + "orgcode,"
                    + "product_code,"
                    + "product_name,"
                    + "min_amount,"
                    + "max_amount,"
                    + "interest_rate,"
                    + "interest_type,"
                    + "rate_type,"
                    + "benchmark_rate_code,"
                    + "min_tenure_months,"
                    + "max_tenure_months,"
                    + "repay_frequency,"
                    + "prin_gl,"
                    + "int_gl,"
                    + "penal_gl,"
                    + "product_status,"
                    + "euser,"
                    + "edate,"
                    + "auser,"
                    + "adate,"
                    + "cuser,"
                    + "cdate"
                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(
                    sql,
                    loan.getOrgcode(),
                    loan.getProduct_code(),
                    loan.getProduct_name(),
                    loan.getMin_amount(),
                    loan.getMax_amount(),
                    loan.getInterest_rate(),
                    loan.getInterest_type(),
                    loan.getRate_type(),
                    loan.getBenchmark_rate_code(),
                    loan.getMin_tenure_months(),
                    loan.getMax_tenure_months(),
                    loan.getRepay_frequency(),
                    loan.getPrin_gl(),
                    loan.getInt_gl(),
                    loan.getPenal_gl(),
                    loan.getProduct_status(),
                    loan.getEuser(),
                    loan.getEdate() != null ? Date.valueOf(loan.getEdate()) : null,
                    loan.getAuser(),
                    loan.getAdate() != null ? Date.valueOf(loan.getAdate()) : null,
                    loan.getCuser(),
                    loan.getCdate() != null ? Date.valueOf(loan.getCdate()) : null);
        }

        return loan;
    }

    public void deleteById(Long orgCode, String productCode) {

        String sql = "DELETE FROM loandev.LOAN101 WHERE orgcode = ? AND product_code = ?";

        jdbcTemplate.update(sql, orgCode, productCode);
    }


}
