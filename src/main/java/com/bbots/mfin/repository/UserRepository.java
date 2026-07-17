package com.bbots.mfin.repository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.OrganizationDto;
import com.bbots.mfin.model.User;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
 
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
 
@Repository
public class UserRepository {
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
 
    private final RowMapper<User> userMapper = (rs, rowNum) -> new User(
            rs.getLong("ORGCODE"),
            rs.getString("USERSCD"),
            rs.getString("TITLE"),
            rs.getString("FNAME"),
            rs.getString("MNAME"),
            rs.getString("LNAME"),
            rs.getDate("REGDATE"),
            rs.getInt("STATUS"),
            rs.getString("BRANCHCD"),
            rs.getString("PICTURE"),
            rs.getString("GENDER"),
            rs.getDate("DOB"),
            rs.getString("EMAILID"),
            rs.getLong("CALLCODE"),
            rs.getInt("COUNTRY"),
            rs.getString("MOBILE"),
            rs.getString("PASSWORD_HASH"),
            rs.getString("PASSWORD_SALT"),
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE"));
 
    public List<User> findAll() {
        String sql = "SELECT u1.*, u2.emailid, u2.callcode, u2.country, u2.mobile, u3.PASSWORD_HASH, u3.PASSWORD_SALT "
                +
                "FROM USER001 u1 " +
                "LEFT JOIN USER002 u2 ON u1.USERSCD = u2.USERSCD AND u1.ORGCODE = u2.ORGCODE " +
                "LEFT JOIN USER003 u3 ON u1.USERSCD = u3.USERSCD AND u1.ORGCODE = u3.ORGCODE";
        return jdbcTemplate.query(sql, userMapper);
    }
 
    public List<User> findAll(int limit, int offset) {
        String sql = "SELECT u1.*, u2.emailid, u2.callcode, u2.country, u2.mobile, u3.PASSWORD_HASH, u3.PASSWORD_SALT "
                +
                "FROM USER001 u1 " +
                "LEFT JOIN USER002 u2 ON u1.USERSCD = u2.USERSCD AND u1.ORGCODE = u2.ORGCODE " +
                "LEFT JOIN USER003 u3 ON u1.USERSCD = u3.USERSCD AND u1.ORGCODE = u3.ORGCODE " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, userMapper, limit, offset);
    }
 
    public long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM USER001", Long.class);
    }
 
    public User findById(Long orgcode, String userscd) {
        String sql = "SELECT u1.*, u2.emailid, u2.callcode, u2.country, u2.mobile, u3.PASSWORD_HASH, u3.PASSWORD_SALT "
                +
                "FROM USER001 u1 " +
                "LEFT JOIN USER002 u2 ON u1.USERSCD = u2.USERSCD AND u1.ORGCODE = u2.ORGCODE " +
                "LEFT JOIN USER003 u3 ON u1.USERSCD = u3.USERSCD AND u1.ORGCODE = u3.ORGCODE " +
                "WHERE u1.ORGCODE = ? AND u1.USERSCD = ?";
        return jdbcTemplate.queryForObject(sql, userMapper, orgcode, userscd);
    }
 
    public void save(User user) {
        // Insert into USER001
        String sql1 = "INSERT INTO USER001 (ORGCODE, USERSCD, TITLE, FNAME, MNAME, LNAME, REGDATE, STATUS, BRANCHCD, PICTURE, GENDER, DOB, EUSER, EDATE) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql1, user.getOrgcode(), user.getUserscd(), user.getTitle(), user.getFname(),
                user.getMname(), user.getLname(),
                user.getRegdate(), user.getStatus(), user.getBranchcd(), user.getPicture(), user.getGender(),
                user.getDob(),
                user.getEuser(), user.getEdate());
 
        // Insert into USER002
        String sql2 = "INSERT INTO USER002 (emailid, USERSCD, callcode, country, mobile, ORGCODE) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql2, user.getEmailid(), user.getUserscd(), user.getCallcode(), user.getCountry(),
                user.getMobile(), user.getOrgcode());
 
        // Insert into USER003
        String sql3 = "INSERT INTO USER003 (ORGCODE, USERSCD, PASSWORD_HASH, PASSWORD_SALT, EUSER, EDATE) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql3, user.getOrgcode(), user.getUserscd(), user.getPasswordHash(), user.getPasswordSalt(),
                user.getEuser(), user.getEdate());
    }
 
    public void update(User user) {
        // Update USER001
        String sql1 = "UPDATE USER001 SET TITLE=?, FNAME=?, MNAME=?, LNAME=?, REGDATE=?, STATUS=?, BRANCHCD=?, PICTURE=?, GENDER=?, DOB=?, CUSER=?, CDATE=? "
                +
                "WHERE ORGCODE=? AND USERSCD=?";
        jdbcTemplate.update(sql1, user.getTitle(), user.getFname(), user.getMname(), user.getLname(), user.getRegdate(),
                user.getStatus(), user.getBranchcd(), user.getPicture(), user.getGender(), user.getDob(),
                user.getCuser(), user.getCdate(), user.getOrgcode(), user.getUserscd());
 
        // Update USER002
        String sql2 = "UPDATE USER002 SET emailid=?, callcode=?, country=?, mobile=? WHERE ORGCODE=? AND USERSCD=?";
        jdbcTemplate.update(sql2, user.getEmailid(), user.getCallcode(), user.getCountry(), user.getMobile(),
                user.getOrgcode(), user.getUserscd());
 
        // Update USER003
        if (user.getPasswordHash() != null) {
            String sql3 = "UPDATE USER003 SET PASSWORD_HASH=?, PASSWORD_SALT=?, CUSER=?, CDATE=? WHERE ORGCODE=? AND USERSCD=?";
            jdbcTemplate.update(sql3, user.getPasswordHash(), user.getPasswordSalt(), user.getCuser(), user.getCdate(),
                    user.getOrgcode(), user.getUserscd());
        }
    }
 
    public void delete(Long orgcode, String userscd) {
        jdbcTemplate.update("DELETE FROM USER003 WHERE ORGCODE = ? AND USERSCD = ?", orgcode, userscd);
        jdbcTemplate.update("DELETE FROM USER002 WHERE ORGCODE = ? AND USERSCD = ?", orgcode, userscd);
        jdbcTemplate.update("DELETE FROM USER001 WHERE ORGCODE = ? AND USERSCD = ?", orgcode, userscd);
    }
 
    public Object[] getUserProfileByUsername(String username) {
        String sql = "SELECT CONCAT(u1.FNAME,' ',u1.LNAME) username, " +
                "u2.emailid email, " +
                "a.ACCESSNAME role " +
                "FROM USER002 u2 " +
                "JOIN USER001 u1 ON u2.USERSCD = u1.USERSCD AND u2.ORGCODE = u1.ORGCODE " +
                "LEFT JOIN USERS002 ura ON u1.USERSCD = ura.USERSCD AND u1.ORGCODE = ura.ORGCODE " +
                "LEFT JOIN ACCESS001 a ON ura.ACCESSCD = a.ACCESSCD AND ura.ORGCODE = a.ORGCODE " +
                "WHERE u2.emailid = ?";
 
        try {
            return jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new Object[] {
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("role")
                    }, username);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return null; // 👈 important
        }
    }
 
    public Optional<OrganizationDto> findByOrgCode(Long orgCode) {
 
        String sql = "SELECT orgcode, name, opendate, edate, logo, country, divisionname, pincode, " +
                "addrline1, addrline2, addrline3, addrline4, addrline5, " +
                "telephone, email, status, indiv " +
                "FROM org001 WHERE orgcode = :orgCode";
 
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgCode", orgCode);
 
        try {
 
            OrganizationDto org = namedJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
 
                OrganizationDto o = new OrganizationDto();
 
                o.setOrgCode(rs.getLong("orgcode"));
                o.setOrgName(rs.getString("name"));
 
                o.setOpenDate(
                        rs.getDate("opendate") != null
                                ? rs.getDate("opendate").toLocalDate()
                                : null);
 
                o.setEdate(
                        rs.getTimestamp("edate") != null
                                ? rs.getTimestamp("edate").toLocalDateTime()
                                : null);
 
                o.setLogo(rs.getString("logo"));
                o.setCountry(rs.getString("country"));
                o.setDivisionName(rs.getString("divisionname"));
                o.setPincode(rs.getString("pincode"));
 
                o.setAddressLine1(rs.getString("addrline1"));
                o.setAddressLine2(rs.getString("addrline2"));
                o.setAddressLine3(rs.getString("addrline3"));
                o.setAddressLine4(rs.getString("addrline4"));
                o.setAddressLine5(rs.getString("addrline5"));
 
                o.setTelephone(rs.getString("telephone"));
                o.setEmail(rs.getString("email"));
 
                o.setStatus(rs.getInt("status"));
                o.setIndiv(rs.getInt("indiv"));
 
                return o;
            });
 
            return Optional.of(org);
 
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
 
    public void insertOrg(OrganizationDto org) {
 
        boolean auth = false;
        String auser = null;
        LocalDateTime adate = null;
 
        if (!auth) {
            auser = org.getAuser();
            adate = LocalDateTime.now();
        }
 
        String sql = "INSERT INTO org001 ("
                + "orgcode, name, opendate, "
                + "logo, country, divisionname, pincode, "
                + "addrline1, addrline2, addrline3, addrline4, addrline5, "
                + "telephone, email, status, indiv, "
                + "auser, adate, "
                + "cuser, cdate"
                + ") VALUES ("
                + ":orgCode, :orgName, :openDate, "
                + ":logo, :country, :divisionName, :pincode, "
                + ":addr1, :addr2, :addr3, :addr4, :addr5, "
                + ":telephone, :email, :status, :indiv, "
                + ":auser, :adate, "
                + ":cuser, CURRENT_TIMESTAMP"
                + ")";
 
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgCode", org.getOrgCode())
                .addValue("orgName", org.getOrgName())
                .addValue("openDate", org.getOpenDate())
                .addValue("logo", org.getLogo())
                .addValue("country", org.getCountry())
                .addValue("divisionName", org.getDivisionName())
                .addValue("pincode", org.getPincode())
                .addValue("addr1", org.getAddressLine1())
                .addValue("addr2", org.getAddressLine2())
                .addValue("addr3", org.getAddressLine3())
                .addValue("addr4", org.getAddressLine4())
                .addValue("addr5", org.getAddressLine5())
                .addValue("telephone", org.getTelephone())
                .addValue("email", org.getEmail())
                .addValue("status", org.getStatus())
                .addValue("indiv", org.getIndiv())
                .addValue("auser", auser)
                .addValue("adate", adate)
                .addValue("cuser", org.getCuser());
 
        namedJdbcTemplate.update(sql, params);
    }
 
    public void updateOrg(OrganizationDto org) {
 
        boolean auth = false;
        String auser = null;
        LocalDateTime adate = null;
 
        if (!auth) {
            auser = org.getAuser();
            adate = LocalDateTime.now();
        }
 
        String sql = "UPDATE org001 "
                + "SET "
                + "name = :orgName, "
                + "opendate = :openDate, "
                + "logo = :logo, "
                + "country = :country, "
                + "divisionname = :divisionName, "
                + "pincode = :pincode, "
                + "addrline1 = :addr1, "
                + "addrline2 = :addr2, "
                + "addrline3 = :addr3, "
                + "addrline4 = :addr4, "
                + "addrline5 = :addr5, "
                + "telephone = :telephone, "
                + "email = :email, "
                + "status = :status, "
                + "indiv = :indiv, "
                + "euser = :euser, "
                + "edate = CURRENT_TIMESTAMP, "
                + "auser = :auser, "
                + "adate = :adate "
                + "WHERE orgcode = :orgCode";
 
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgCode", org.getOrgCode())
                .addValue("orgName", org.getOrgName())
                .addValue("openDate", org.getOpenDate())
                .addValue("logo", org.getLogo())
                .addValue("country", org.getCountry())
                .addValue("divisionName", org.getDivisionName())
                .addValue("pincode", org.getPincode())
                .addValue("addr1", org.getAddressLine1())
                .addValue("addr2", org.getAddressLine2())
                .addValue("addr3", org.getAddressLine3())
                .addValue("addr4", org.getAddressLine4())
                .addValue("addr5", org.getAddressLine5())
                .addValue("telephone", org.getTelephone())
                .addValue("email", org.getEmail())
                .addValue("status", org.getStatus())
                .addValue("indiv", org.getIndiv())
                .addValue("euser", org.getEuser())
                .addValue("auser", auser)
                .addValue("adate", adate);
 
        namedJdbcTemplate.update(sql, params);
    }
 
}