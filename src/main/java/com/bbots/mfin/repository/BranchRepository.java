package com.bbots.mfin.repository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.BranchDto;
import com.bbots.mfin.model.Branch;

@Repository
public class BranchRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Branch> branchMapper = (rs, rowNum) -> new Branch(
            rs.getLong("ORGCODE"),
            rs.getLong("BRNCD"),
            rs.getString("BRNNAME"),
            rs.getDate("OPENDATE"),
            rs.getString("ADDRESS"),
            rs.getString("COUNTRY"),
            rs.getString("DIVISIONNAME"),
            rs.getString("PINCODE"),
            rs.getString("ADDRLINE1"),
            rs.getString("ADDRLINE2"),
            rs.getString("ADDRLINE3"),
            rs.getString("ADDRLINE4"),
            rs.getString("ADDRLINE5"),
            rs.getString("TELEPHONE"),
            rs.getString("EMAIL"),
            rs.getInt("STATUS"),
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE"),
            rs.getInt("HEADBRN")
    );

    private static final String BASE_SELECT =
            "SELECT ORGCODE, BRNCD, BRNNAME, OPENDATE, ADDRESS, COUNTRY, " +
            "DIVISIONNAME, PINCODE, ADDRLINE1, ADDRLINE2, ADDRLINE3, ADDRLINE4, ADDRLINE5, " +
            "TELEPHONE, EMAIL, STATUS, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE, HEADBRN " +
            "FROM BRANCH001";

    public List<Branch> findAll() {
        return jdbcTemplate.query(BASE_SELECT, branchMapper);
    }

    public List<Branch> findAll(int limit, int offset) {
        String sql = BASE_SELECT + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, branchMapper, limit, offset);
    }

    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BRANCH001", Long.class);
    }

    public Branch findById(Long orgcode, Long brncd) {
        String sql = BASE_SELECT + " WHERE ORGCODE = ? AND BRNCD = ?";
        return jdbcTemplate.queryForObject(sql, branchMapper, orgcode, brncd);
    }

    public void save(Branch branch) {
        String sql = "INSERT INTO BRANCH001 " +
                "(ORGCODE, BRNCD, BRNNAME, OPENDATE, ADDRESS, COUNTRY, DIVISIONNAME, " +
                "PINCODE, ADDRLINE1, ADDRLINE2, ADDRLINE3, ADDRLINE4, ADDRLINE5, " +
                "TELEPHONE, EMAIL, STATUS, EUSER, EDATE, HEADBRN) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                branch.getOrgcode(), branch.getBrncd(), branch.getBrnname(),
                branch.getOpendate(), branch.getAddress(), branch.getCountry(), branch.getDivisionname(),
                branch.getPincode(), branch.getAddrline1(), branch.getAddrline2(), branch.getAddrline3(),
                branch.getAddrline4(), branch.getAddrline5(), branch.getTelephone(), branch.getEmail(),
                branch.getStatus(), branch.getEuser(), branch.getEdate(), branch.getHeadbrn());
    }

    public void update(Branch branch) {
        String sql = "UPDATE BRANCH001 SET " +
                "BRNNAME=?, OPENDATE=?, ADDRESS=?, COUNTRY=?, DIVISIONNAME=?, " +
                "PINCODE=?, ADDRLINE1=?, ADDRLINE2=?, ADDRLINE3=?, ADDRLINE4=?, ADDRLINE5=?, " +
                "TELEPHONE=?, EMAIL=?, STATUS=?, CUSER=?, CDATE=?, HEADBRN=? " +
                "WHERE ORGCODE=? AND BRNCD=?";
        jdbcTemplate.update(sql,
                branch.getBrnname(), branch.getOpendate(),
                branch.getAddress(), branch.getCountry(), branch.getDivisionname(),
                branch.getPincode(), branch.getAddrline1(), branch.getAddrline2(),
                branch.getAddrline3(), branch.getAddrline4(), branch.getAddrline5(),
                branch.getTelephone(), branch.getEmail(), branch.getStatus(),
                branch.getCuser(), branch.getCdate(), branch.getHeadbrn(),
                branch.getOrgcode(), branch.getBrncd());
    }

    public void delete(Long orgcode, Long brncd) {
        jdbcTemplate.update("DELETE FROM BRANCH001 WHERE ORGCODE = ? AND BRNCD = ?", orgcode, brncd);
    }

	public void insertBranch(BranchDto branch) {
	    boolean auth = false;
	    java.time.LocalDateTime now = java.time.LocalDateTime.now();
	    String auser = null;
	    java.sql.Timestamp adate = null;
	    if (!auth) {
	        auser = branch.getAuser();
	        adate = java.sql.Timestamp.valueOf(now);
	    }
	    String sql =
	    	    "INSERT INTO branch001 (" +
	    	    "orgcode, brncd, brnname, opendate, country, divisionname, " +
	    	    "pincode, addrline1, addrline2, addrline3, addrline4, addrline5, " +
	    	    "telephone, email, status, headbrn, " +
	    	    "euser, edate, auser, adate, cuser, cdate" +
	    	    ") VALUES (" +
	    	    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
	    	    "NULL, NULL, ?, ?, ?, CURRENT_TIMESTAMP" +
	    	    ")";
	    jdbcTemplate.update(
	            sql,
	            branch.getOrgCode(),
	            branch.getBrncd(),
	            branch.getBrnname(),
	            branch.getOpenDate() != null ? java.sql.Date.valueOf(branch.getOpenDate()) : null,
	            branch.getCountry(),
	            branch.getDivisionName(),
	            branch.getPincode(),
	            branch.getAddrline1(),
	            branch.getAddrline2(),
	            branch.getAddrline3(),
	            branch.getAddrline4(),
	            branch.getAddrline5(),
	            branch.getTelephone(),
	            branch.getEmail(),
	            branch.getStatus(),
	            branch.getHeadbrn(),
	            auser,
	            adate,
	            branch.getCuser()
	    );
	}
	
	public void updateBranch(BranchDto branch) {
	    boolean auth = false;
	    java.time.LocalDateTime now = java.time.LocalDateTime.now();
	    String auser = null;
	    java.sql.Timestamp adate = null;
	    if (!auth) {
	        auser = branch.getAuser();
	        adate = java.sql.Timestamp.valueOf(now);
	    }
	    String sql =
	            "UPDATE branch001 SET " +
	            "brnname = ?, " +
	            "opendate = ?, " +
	            "country = ?, " +
	            "divisionname = ?, " +
	            "pincode = ?, " +
	            "addrline1 = ?, " +
	            "addrline2 = ?, " +
	            "addrline3 = ?, " +
	            "addrline4 = ?, " +
	            "addrline5 = ?, " +
	            "telephone = ?, " +
	            "email = ?, " +
	            "status = ?, " +
	            "headbrn = ?, " +
	            "euser = ?, " +
	            "edate = CURRENT_TIMESTAMP, " +
	            "auser = ?, " +
	            "adate = ? " +
	            "WHERE orgcode = ? " +
	            "AND brncd = ?";

	    
	    jdbcTemplate.update(
	            sql,
	            branch.getBrnname(),
	            branch.getOpenDate() != null ? java.sql.Date.valueOf(branch.getOpenDate()) : null,
	            branch.getCountry(),
	            branch.getDivisionName(),
	            branch.getPincode(),
	            branch.getAddrline1(),
	            branch.getAddrline2(),
	            branch.getAddrline3(),
	            branch.getAddrline4(),
	            branch.getAddrline5(),
	            branch.getTelephone(),
	            branch.getEmail(),
	            branch.getStatus() != null && branch.getStatus() ? 1 : 0,
	            branch.getHeadbrn() != null && branch.getHeadbrn() ? 1 : 0,
	            branch.getEuser(),
	            auser,
	            adate,
	            branch.getOrgCode(),
	            branch.getBrncd()
	    );
	}

	public java.sql.Timestamp getExistingDate(Long orgcode, Long brncd) {
	    String sql = "SELECT adate FROM branch001 WHERE orgcode = ? AND brncd = ?";
	    java.util.List<java.sql.Timestamp> result = jdbcTemplate.query(
	            sql,
	            (rs, rowNum) -> rs.getTimestamp("adate"),
	            orgcode,
	            brncd
	    );
	    return result.isEmpty() ? null : result.get(0);
	}
}
