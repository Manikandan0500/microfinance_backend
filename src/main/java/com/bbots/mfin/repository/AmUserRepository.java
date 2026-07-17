package com.bbots.mfin.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.UserDetailsDto;
import com.bbots.mfin.dto.UserDto;
import com.bbots.mfin.dto.UserMappedProducts;
import com.bbots.mfin.exception.UserBlockedException;

@Repository
public class AmUserRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public AmUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public UserDto findByEmail(String email, Integer productCode) {

		// String userSql = "SELECT u1.orgcode, u1.userscd, u1.edate, u1.fname,
		// u1.mname, u1.lname, "
		// + "u2.emailid, u3.password_hash, u3.password_salt " + "FROM
		// loandev.user001 u1 "
		// + "JOIN loandev.user002 u2 ON u1.orgcode = u2.orgcode AND u1.userscd =
		// u2.userscd "
		// + "JOIN loandev.user003 u3 ON u1.orgcode = u3.orgcode AND u1.userscd =
		// u3.userscd "
		// + "WHERE u2.emailid = :emailid AND u1.status = 1";

		String userSql = "SELECT u1.orgcode, u1.userscd, u1.edate, u1.fname, u1.mname, u1.lname, "
				+ "u2.emailid, u3.password_hash, u3.password_salt " + "FROM loandev.user001 u1 "
				+ "JOIN loandev.user002 u2 ON u1.orgcode = u2.orgcode AND u1.userscd = u2.userscd "
				+ "JOIN loandev.user003 u3 ON u1.orgcode = u3.orgcode AND u1.userscd = u3.userscd "
				+ "WHERE u2.emailid = :emailid AND u1.status = 1";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("emailid", email);

		try {
			List<UserDto> users = jdbcTemplate.query(userSql, params, (rs, rowNum) -> {

				UserDto u = new UserDto();

				u.setOrgCode(rs.getLong("orgcode"));
				u.setUserScd(rs.getString("userscd"));

				String fname = rs.getString("fname");
				String mname = rs.getString("mname");
				String lname = rs.getString("lname");

				u.setfName(fname);
				u.setmName(mname);
				u.setlName(lname);

				StringBuilder fullName = new StringBuilder();
				if (fname != null)
					fullName.append(fname);
				if (mname != null && !mname.isEmpty())
					fullName.append(" ").append(mname);
				if (lname != null && !lname.isEmpty())
					fullName.append(" ").append(lname);

				u.setUserName(fullName.toString());

				u.setEmail(rs.getString("emailid"));
				u.setPassword(rs.getString("password_hash"));
				u.setPassword_salt(rs.getString("password_salt"));

				if (rs.getTimestamp("edate") != null) {
					u.setUsermodDate(rs.getTimestamp("edate").toLocalDateTime());
				}

				return u;
			});

			if (users.isEmpty()) {
				throw new UsernameNotFoundException("User not found with email: " + email);
			}

			UserDto user = users.get(0);
			checkUserBlocked(user.getOrgCode(), user.getUserScd());

			String accessSql = "SELECT c.accesstype, c.accesscd " + "FROM loandev.user004 a "
					+ "JOIN loandev.access001 c ON c.accesscd = a.accesscd and c.orgcode = a.orgcode "
					+ "WHERE a.orgcode = :orgCode AND a.userscd = :usersCd AND a.prodcode = :prodCode";

			MapSqlParameterSource params2 = new MapSqlParameterSource().addValue("orgCode", user.getOrgCode())
					.addValue("usersCd", user.getUserScd()).addValue("prodCode", productCode);

			List<Map<String, Object>> accessList = jdbcTemplate.query(accessSql, params2, (rs, rowNum) -> {
				Map<String, Object> map = new HashMap<>();
				map.put("accesstype", rs.getString("accesstype"));
				map.put("accesscd", rs.getInt("accesscd"));
				return map;
			});

			if (accessList.isEmpty()) {
				throw new UsernameNotFoundException("User not mapped with product");
			}

			Map<String, Object> firstAccess = accessList.get(0);
			user.setRoleType((String) firstAccess.get("accesstype"));
			user.setAccessCd((Integer) firstAccess.get("accesscd"));

			return user;

		} catch (EmptyResultDataAccessException e) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		} catch (Exception e) {
			throw new RuntimeException("Database query failed: " + e.getMessage(), e);
		}
	}

	public void save(UserDto user) {
		String sql = "INSERT INTO loandev.user001 (orgcode, fname, lname, email, password, is_online) "
				+ "VALUES (:orgCode, :fName, :lName, :email, :password, :isOnline)";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgCode", user.getOrgCode())
				.addValue("fName", user.getfName()).addValue("lName", user.getlName())
				.addValue("email", user.getEmail()).addValue("password", user.getPassword())
				.addValue("isOnline", user.getIsOnline());

		jdbcTemplate.update(sql, params);
	}

	public Optional<UserDetailsDto> getUserDetails(String senderId, Long orgCode) {

		String sql = "SELECT " + "u1.userscd, u1.orgcode, " + "u1.fname, u1.mname, u1.lname, "
				+ "u1.gender, u1.title, u1.picture, u1.dob, " +

				"u1.euser, u1.edate, " + "u1.auser, u1.adate, " + "u1.cuser, u1.cdate, " +

				"u2.emailid, u2.mobile, u2.callcode, u2.country " +

				"FROM loandev.user001 u1 " +

				"LEFT JOIN loandev.user002 u2 " + "ON u1.orgcode = u2.orgcode " + "AND u1.userscd = u2.userscd " +

				"WHERE u1.userscd = :senderId " + "AND u1.orgcode = :orgCode";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("senderId", senderId);
		params.addValue("orgCode", orgCode);

		try {

			UserDetailsDto user = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {

				UserDetailsDto dto = new UserDetailsDto();

				dto.setUserScd(rs.getString("userscd"));
				dto.setOrgCode(rs.getLong("orgcode"));

				dto.setfName(rs.getString("fname"));
				dto.setmName(rs.getString("mname"));
				dto.setlName(rs.getString("lname"));

				dto.setTitle(rs.getString("title"));
				dto.setGender(rs.getString("gender"));

				Date dob = rs.getDate("dob");

				dto.setDob(dob != null ? dob.toString() : null);

				dto.setPicture(rs.getString("picture"));

				dto.setEmail(rs.getString("emailid"));
				dto.setMobile(rs.getString("mobile"));
				dto.setCallcode(rs.getString("callcode"));
				dto.setCountry(rs.getString("country"));

				dto.setEuser(rs.getString("euser"));

				Timestamp edate = rs.getTimestamp("edate");

				dto.setEdate(edate != null ? edate.toLocalDateTime() : null);

				dto.setAuser(rs.getString("auser"));

				Timestamp adate = rs.getTimestamp("adate");

				dto.setAdate(adate != null ? adate.toLocalDateTime() : null);

				dto.setCuser(rs.getString("cuser"));

				Timestamp cdate = rs.getTimestamp("cdate");

				dto.setCdate(cdate != null ? cdate.toLocalDateTime() : null);

				return dto;
			});

			if (user != null) {
				user.setProducts(getUserMappedProducts(senderId, orgCode));
			}

			return Optional.ofNullable(user);

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private List<UserMappedProducts> getUserMappedProducts(String senderId, Long orgCode) {

		String sql = "SELECT " + "a.prodcode, " + "a.status, " + "a.accesscd, " + "a.userscd, " +

				"a.euser, " + "a.edate, " + "a.auser, " + "a.adate, " + "a.cuser, " + "a.cdate, " +

				"c.prodname, " + "c.home_url, " + "c.logo " +

				"FROM loandev.user004 a " +

				"JOIN loandev.product002 b " + "ON a.orgcode = b.orgcode " + "AND a.prodcode = b.prodcode " +

				"JOIN loandev.product001 c " + "ON c.prodcode = a.prodcode " + "AND c.status = true " +

				"WHERE a.userscd = :senderId " + "AND a.orgcode = :orgCode " + "AND a.status = true "
				+ "AND b.status = true " +

				"ORDER BY a.prodcode ASC";

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("senderId", senderId);
		params.addValue("orgCode", orgCode);

		return jdbcTemplate.query(sql, params, (rs, rowNum) -> {

			UserMappedProducts p = new UserMappedProducts();

			p.setProdcode(rs.getLong("prodcode"));
			p.setProdname(rs.getString("prodname"));
			p.setHomeUrl(rs.getString("home_url"));

			p.setOrgcode(orgCode);

			p.setStatus(rs.getBoolean("status"));

			p.setAccesscd(rs.getLong("accesscd"));
			p.setUserscd(rs.getString("userscd"));

			p.setLogo(rs.getString("logo"));

			p.setEuser(rs.getString("euser"));

			Timestamp edate = rs.getTimestamp("edate");

			p.setEdate(edate != null ? edate.toLocalDateTime() : null);

			p.setAuser(rs.getString("auser"));

			Timestamp adate = rs.getTimestamp("adate");

			p.setAdate(adate != null ? adate.toLocalDateTime() : null);

			p.setCuser(rs.getString("cuser"));

			Timestamp cdate = rs.getTimestamp("cdate");

			p.setCdate(cdate != null ? cdate.toLocalDateTime() : null);

			return p;
		});
	}

	public int upsertPasswordByUserScd(Long orgCode, String userScd, String encodedPassword) {

		String historySql = "INSERT INTO loandev.user005 (orgcode, userscd, changed_on, password_hash, changed_by) "
				+ "VALUES (:orgCode, :usersCd, CURRENT_TIMESTAMP, :password, :usersCd)";
		MapSqlParameterSource checkParams = new MapSqlParameterSource().addValue("orgCode", orgCode)
				.addValue("usersCd", userScd).addValue("password", encodedPassword);
		jdbcTemplate.update(historySql, checkParams);

		String sql = "INSERT INTO loandev.user003 (orgcode, userscd, password_hash, cuser, cdate) "
				+ "VALUES (:orgCode, :userScd, :password, :user, CURRENT_TIMESTAMP) "
				+ "ON CONFLICT (orgcode, userscd) " + "DO UPDATE SET " + "password_hash = EXCLUDED.password_hash, "
				+ "euser = :user, " + "edate = CURRENT_TIMESTAMP";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgCode", orgCode)
				.addValue("userScd", userScd).addValue("password", encodedPassword).addValue("user", userScd);

		return jdbcTemplate.update(sql, params);
	}

	public Optional<UserDto> verifyEmail(String email) {

		String sql = "SELECT u1.orgcode, u1.userscd, u1.fname, u1.lname, " + "u2.emailid "
				+ "FROM loandev.user001 u1 "
				+ "JOIN loandev.user002 u2 ON u1.orgcode = u2.orgcode AND u1.userscd = u2.userscd "
				+ "WHERE u2.emailid = :email " + "AND u1.status = 1 ";

		MapSqlParameterSource params = new MapSqlParameterSource("email", email);

		try {
			List<UserDto> users = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
				UserDto user = new UserDto();

				user.setOrgCode(rs.getLong("orgcode"));
				user.setUserScd(rs.getString("userscd"));
				user.setfName(rs.getString("fname"));
				user.setlName(rs.getString("lname"));
				user.setEmail(rs.getString("emailid"));
				return user;
			});

			return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));

		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public UserDto findUser(String email) {
		return verifyEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}

	public List<String> getPasswordHistory(Long orgCode, String userScd) {

		String sql = "SELECT password_hash FROM loandev.user005 "
				+ "WHERE orgcode = :orgCode AND userscd = :usersCd";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgCode", orgCode).addValue("usersCd",
				userScd);

		return jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getString("password_hash"));
	}

	public String getOldPasswordByUserScd(Long orgCode, String userScd) {

		String sql = "SELECT password_hash " + "FROM loandev.user003 " + "WHERE orgcode = :orgCode "
				+ "AND userscd = :userScd";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgCode", orgCode).addValue("userScd",
				userScd);

		try {
			return jdbcTemplate.queryForObject(sql, params, String.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Optional<UserDto> updateUserDetails(Long orgCode, String userScd, String fName, String lName, String email,
			String mobile, String picture) {
		String sql1 = "UPDATE loandev.user001 SET fname = :fName, lname = :lName, picture = :picture "
				+ "WHERE orgcode = :orgCode AND userscd = :userScd";

		MapSqlParameterSource params1 = new MapSqlParameterSource().addValue("orgCode", orgCode)
				.addValue("userScd", userScd).addValue("fName", fName).addValue("lName", lName)
				.addValue("picture", picture);

		try {
			int rowsUpdated1 = jdbcTemplate.update(sql1, params1);

			if (rowsUpdated1 > 0) {
				String sql2 = "UPDATE loandev.user002 SET emailid = :email, mobile = :mobile "
						+ "WHERE orgcode = :orgCode AND userscd = :userScd";

				MapSqlParameterSource params2 = new MapSqlParameterSource().addValue("orgCode", orgCode)
						.addValue("userScd", userScd).addValue("email", email).addValue("mobile", mobile);

				int rowsUpdated2 = jdbcTemplate.update(sql2, params2);

				UserDto dto = new UserDto();
				dto.setUserScd(userScd);
				dto.setOrgCode(orgCode);
				dto.setfName(fName);
				dto.setlName(lName);
				dto.setEmail(email);
				dto.setMobile(mobile);
				dto.setPicture(picture);
				return Optional.of(dto);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private void checkUserBlocked(Long orgCode, String userScd) {

		String sql = "SELECT status, reason " + "FROM loandev.user006 "
				+ "WHERE orgcode = :orgCode AND userscd = :usersCd";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgCode", orgCode).addValue("usersCd",
				userScd);

		List<Map<String, Object>> result = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("status", rs.getBoolean("status"));
			map.put("reason", rs.getString("reason"));
			return map;
		});

		if (!result.isEmpty()) {
			Boolean isBlocked = (Boolean) result.get(0).get("status");

			if (Boolean.TRUE.equals(isBlocked)) {
				String reason = (String) result.get(0).get("reason");

				throw new UserBlockedException("User is blocked" + (reason != null ? ": " + reason : ""));
			}
		}
	}

	public Optional<UserDto> findByUserScd(String userScd, Long orgCode) {
		String sql = "SELECT u1.orgcode, u1.userscd, u1.fname, u1.mname, u1.lname, " +
				"u1.gender, u1.title, u1.edate, " +
				"u2.emailid " +
				"FROM loandev.user001 u1 " +
				"JOIN loandev.user002 u2 ON u1.orgcode = u2.orgcode " +
				"AND u1.userscd = u2.userscd " +
				"WHERE u1.userscd = :userScd " +
				"AND u1.orgcode = :orgCode " +
				"AND u1.status = 1";

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("userScd", userScd)
				.addValue("orgCode", orgCode);

		try {
			UserDto user = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
				UserDto u = new UserDto();
				u.setOrgCode(rs.getLong("orgcode"));
				u.setUserScd(rs.getString("userscd"));
				u.setfName(rs.getString("fname"));
				u.setmName(rs.getString("mname"));
				u.setlName(rs.getString("lname"));
				u.setGender(rs.getString("gender"));
				u.setTitle(rs.getString("title"));
				u.setEmail(rs.getString("emailid"));
				u.setUsermodDate(rs.getTimestamp("edate") != null ? rs.getTimestamp("edate").toLocalDateTime() : null);
				return u;
			});
			return Optional.of(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public boolean existsByUserScd(String userScd, Long orgCode) {
		String sql = "SELECT COUNT(*) FROM loandev.user001 WHERE userscd = :userScd AND orgcode = :orgCode";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("userScd", userScd)
				.addValue("orgCode", orgCode);
		Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return count != null && count > 0;
	}

	public boolean existsInUser002(String userScd, Long orgCode) {
		String sql = "SELECT COUNT(*) FROM loandev.user002 WHERE userscd = :userScd AND orgcode = :orgCode";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("userScd", userScd)
				.addValue("orgCode", orgCode);
		Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return count != null && count > 0;
	}

	public void updateUser001(UserDto user) {
		boolean auth = false;
		LocalDateTime now = LocalDateTime.now();
		String auser = null;
		Timestamp adate = null;
		if (!auth) {
			auser = user.getUserName() != null ? user.getUserName() : "system";
			adate = Timestamp.valueOf(now);
		}
		String sql = "UPDATE loandev.user001 SET "
				+ "fname = :fName, mname = :mName, lname = :lName, brncd = :brncd, status = :status, gender = :gender, "
				+ "title = :title, dob = :dob, picture = :picture, "
				+ "euser = :euser, edate = CURRENT_TIMESTAMP, "
				+ "auser = :auser, adate = :adate "
				+ "WHERE orgcode = :orgCode AND userscd = :userScd";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("fName", user.getfName()).addValue("mName", user.getmName())
				.addValue("lName", user.getlName())
				.addValue("gender", user.getGender()).addValue("title", user.getTitle())
				.addValue("brncd", user.getBrncd())
				.addValue("status", user.getStatus())
				.addValue("dob",
						user.getDob() != null ? java.sql.Date.valueOf(java.time.LocalDate.parse(user.getDob())) : null)
				.addValue("picture", user.getPicture())
				.addValue("euser", user.getEuser() != null ? user.getEuser() : "system")
				.addValue("auser", auser).addValue("adate", adate)
				.addValue("orgCode", user.getOrgCode()).addValue("userScd", user.getUserScd());
		jdbcTemplate.update(sql, params);
	}

	private Long parseCallcode(String callcode) {
		if (callcode == null || callcode.isEmpty())
			return null;
		try {
			return Long.valueOf(callcode.replace("+", ""));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private Integer parseCountry(String country) {
		if (country == null || country.isEmpty())
			return null;
		try {
			return Integer.valueOf(country);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public void updateUser002(UserDto user) {
		String sql = "UPDATE loandev.user002 SET emailid=:email, "
				+ "callcode=:callcode, country=:country, mobile=:mobile "
				+ "WHERE orgcode=:orgCode AND userscd=:userScd";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", user.getEmail())
				.addValue("callcode", parseCallcode(user.getCallcode()))
				.addValue("country", parseCountry(user.getCountry()))
				.addValue("mobile", user.getMobile()).addValue("orgCode", user.getOrgCode())
				.addValue("userScd", user.getUserScd());
		jdbcTemplate.update(sql, params);
	}

	public void insertUser001(UserDto user) {
		boolean auth = false;
		LocalDateTime now = LocalDateTime.now();
		String auser = null;
		Timestamp adate = null;
		if (!auth) {
			auser = user.getUserName() != null ? user.getUserName() : "system";
			adate = Timestamp.valueOf(now);
		}
		String sql = "INSERT INTO user001 ("
				+ "orgcode, userscd, fname, mname, lname, "
				+ "gender, title, brncd, status, "
				+ "dob, picture, euser, edate, auser, adate, cuser, cdate"
				+ ") VALUES ("
				+ ":orgCode, :userScd, :fName, :mName, :lName, "
				+ ":gender, :title, :brncd, :status, "
				+ ":dob, :picture, NULL, NULL, :auser, :adate, :cuser, CURRENT_TIMESTAMP"
				+ ")";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("orgCode", user.getOrgCode()).addValue("userScd", user.getUserScd())
				.addValue("fName", user.getfName()).addValue("mName", user.getmName())
				.addValue("lName", user.getlName()).addValue("gender", user.getGender())
				.addValue("title", user.getTitle())
				.addValue("brncd", user.getBrncd())
				.addValue("status", user.getStatus())
				.addValue("dob",
						user.getDob() != null ? java.sql.Date.valueOf(java.time.LocalDate.parse(user.getDob())) : null)
				.addValue("picture", user.getPicture()).addValue("auser", auser).addValue("adate", adate)
				.addValue("cuser", user.getUserName() != null ? user.getUserName() : "system");
		jdbcTemplate.update(sql, params);
	}

	public void insertUser002(UserDto user) {
		String sql = "INSERT INTO loandev.user002 (orgcode, userscd, emailid, callcode, country, mobile) "
				+ "VALUES (:orgCode, :userScd, :email, :callcode, :country, :mobile)";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgCode", user.getOrgCode())
				.addValue("userScd", user.getUserScd()).addValue("email", user.getEmail())
				.addValue("callcode", parseCallcode(user.getCallcode()))
				.addValue("country", parseCountry(user.getCountry()))
				.addValue("mobile", user.getMobile());
		jdbcTemplate.update(sql, params);
	}

	public void insertFullUser(UserDto user) {
		insertUser001(user);
		insertUser002(user);
	}

	public void upsertUserProduct(Long orgCode, String userScd, UserMappedProducts product) {
		boolean auth = false;
		LocalDateTime now = LocalDateTime.now();
		String auser = null;
		Timestamp adate = null;
		if (!auth) {
			auser = product.getAuser() != null ? product.getAuser() : "system";
			adate = Timestamp.valueOf(now);
		}
		String sql = "INSERT INTO loandev.user004 ("
				+ "orgcode, userscd, prodcode, accesscd, status, "
				+ "euser, edate, auser, adate, cuser, cdate "
				+ ") VALUES ("
				+ ":orgCode, :userScd, :prodcode, :accesscd, :status, "
				+ "NULL, NULL, :auser, :adate, :cuser, CURRENT_TIMESTAMP"
				+ ") "
				+ "ON CONFLICT (orgcode, userscd, prodcode) DO UPDATE SET "
				+ "accesscd = EXCLUDED.accesscd, status = EXCLUDED.status, "
				+ "euser = :euser, edate = CURRENT_TIMESTAMP, "
				+ "auser = :auser, adate = :adate "
				+ "WHERE loandev.user004.accesscd IS DISTINCT FROM EXCLUDED.accesscd OR "
				+ "loandev.user004.status IS DISTINCT FROM EXCLUDED.status OR "
				+ "loandev.user004.euser IS DISTINCT FROM :euser OR "
				+ "loandev.user004.auser IS DISTINCT FROM :auser";

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("orgCode", orgCode).addValue("userScd", userScd)
				.addValue("prodcode", product.getProdcode()).addValue("accesscd", product.getAccesscd())
				.addValue("status", product.getStatus() != null ? product.getStatus() : false)
				.addValue("euser", product.getEuser() != null ? product.getEuser() : "system")
				.addValue("auser", auser).addValue("adate", adate)
				.addValue("cuser", product.getCuser() != null ? product.getCuser() : "system");
		jdbcTemplate.update(sql, params);
	}

	public Map<String, LocalDateTime> getModDates(String userScd, Long orgCode) {
		String sql = "SELECT o.adate as org_adate, u.adate as user_adate " +
				"FROM loandev.org001 o " +
				"LEFT JOIN loandev.user001 u ON u.orgcode = o.orgcode AND u.userscd = :userScd " +
				"WHERE o.orgcode = :orgCode";

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("userScd", userScd)
				.addValue("orgCode", orgCode);

		java.util.Map<String, LocalDateTime> dates = new HashMap<>();
		try {
			jdbcTemplate.query(sql, params, rs -> {
				java.sql.Timestamp orgAdate = rs.getTimestamp("org_adate");
				java.sql.Timestamp userAdate = null;
				try {
					userAdate = rs.getTimestamp("user_adate");
				} catch (Exception ignore) {
				}

				if (orgAdate != null) {
					dates.put("orgModDate", orgAdate.toLocalDateTime());
				}
				if (userAdate != null) {
					dates.put("userModDate", userAdate.toLocalDateTime());
				}
			});
		} catch (Exception e) {
		}

		return dates;
	}
}
