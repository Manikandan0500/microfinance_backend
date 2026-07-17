package com.bbots.mfin.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.ProductDto;
import com.bbots.mfin.dto.ProductMappedDto;

@Repository
public class ProductRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<com.bbots.mfin.dto.ProductDto> findByProdCode(Long prodCode) {
		String sql = "SELECT orgcode, prodcode, prodname, home_url, status, euser, edate, auser, adate, cuser, cdate, logo "
				+ "FROM product001 WHERE prodcode = :prodCode";
		MapSqlParameterSource params = new MapSqlParameterSource("prodCode", prodCode);

		try {
			ProductDto dto = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
				ProductDto item = new ProductDto();
				item.setOrgcode(rs.getLong("orgcode"));
				item.setProdcode(rs.getLong("prodcode"));
				item.setProdname(rs.getString("prodname"));
				item.setHomeUrl(rs.getString("home_url"));
				item.setStatus(rs.getBoolean("status"));
				item.setEuser(rs.getString("euser"));
				item.setEdate(rs.getObject("edate", java.time.LocalDateTime.class));
				item.setAuser(rs.getString("auser"));
				item.setAdate(rs.getObject("adate", java.time.LocalDateTime.class));
				item.setCuser(rs.getString("cuser"));
				item.setCdate(rs.getObject("cdate", java.time.LocalDateTime.class));
				item.setLogo(rs.getString("logo"));
				return item;
			});
			return Optional.ofNullable(dto);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<com.bbots.mfin.dto.ProductDto> findAll() {
		String sql = "SELECT orgcode, prodcode, prodname, home_url, status, euser, edate, auser, adate, cuser, cdate, logo "
				+ "FROM product001 ORDER BY prodcode";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			com.bbots.mfin.dto.ProductDto item = new com.bbots.mfin.dto.ProductDto();
			item.setOrgcode(rs.getLong("orgcode"));
			item.setProdcode(rs.getLong("prodcode"));
			item.setProdname(rs.getString("prodname"));
			item.setHomeUrl(rs.getString("home_url"));
			item.setStatus(rs.getBoolean("status"));
			item.setEuser(rs.getString("euser"));
			item.setEdate(rs.getObject("edate", java.time.LocalDateTime.class));
			item.setAuser(rs.getString("auser"));
			item.setAdate(rs.getObject("adate", java.time.LocalDateTime.class));
			item.setCuser(rs.getString("cuser"));
			item.setCdate(rs.getObject("cdate", java.time.LocalDateTime.class));
			item.setLogo(rs.getString("logo"));
			return item;
		});
	}

	public boolean existsByProdCode(Long prodCode) {
		String sql = "SELECT COUNT(*) FROM product001 WHERE prodcode = :prodCode";
		MapSqlParameterSource params = new MapSqlParameterSource("prodCode", prodCode);

		Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return count != null && count > 0;
	}

	public boolean existsByOrgCodeAndProdCode(Long orgCode, Long prodCode) {
		String sql = "SELECT COUNT(*) FROM product001 WHERE orgcode = :orgCode AND prodcode = :prodCode";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgCode", orgCode).addValue("prodCode",
				prodCode);

		Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return count != null && count > 0;
	}

	public void saveAndUpdate(com.bbots.mfin.dto.ProductDto dto) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = null;
		LocalDateTime date = LocalDateTime.now();
		if (existsByProdCode(dto.getProdcode())) {
			sql = "UPDATE product001 SET prodname = :prodname, "
					+ "home_url = :homeUrl, status = :status, logo = :logo , adate=:adate,auser=:auser,edate=:edate,euser=:euser "
					+ "WHERE orgcode = :orgcode AND prodcode = :prodcode";
			params.addValue("edate", date);
			params.addValue("euser", dto.getUserName());
		} else {
			sql = "INSERT INTO product001 (orgcode, prodcode, prodname, home_url, status, logo,cdate,cuser,adate,auser) "
					+ "VALUES (:orgcode, :prodcode, :prodname, :homeUrl, :status, :logo,:cdate,:cuser,:adate,:auser)";
			params.addValue("cdate", date);
			params.addValue("cuser", dto.getUserName());
		}

		params.addValue("orgcode", dto.getOrgcode());
		params.addValue("prodcode", dto.getProdcode());
		params.addValue("prodname", dto.getProdname());
		params.addValue("homeUrl", dto.getHomeUrl());
		params.addValue("status", dto.getStatus());
		params.addValue("logo", dto.getLogo());
		Boolean auth = false;
		if (!auth) {
			params.addValue("auser", dto.getUserName());
			params.addValue("adate", date);
		} else {
			params.addValue("auser", null);
			params.addValue("adate", null);
		}
		jdbcTemplate.update(sql, params);
	}

	public void update(com.bbots.mfin.dto.ProductDto dto) {
		String sql = "UPDATE product001 SET prodname = :prodname, "
				+ "home_url = :homeUrl, status = :status, logo = :logo "
				+ "WHERE orgcode = :orgcode AND prodcode = :prodcode";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("orgcode", dto.getOrgcode())
				.addValue("prodcode", dto.getProdcode()).addValue("prodname", dto.getProdname())
				.addValue("homeUrl", dto.getHomeUrl()).addValue("status", dto.getStatus())
				.addValue("logo", dto.getLogo());
		jdbcTemplate.update(sql, params);
	}

	public void deleteByProdCode(Long prodCode) {
	}

	public void deleteByProdCodeAndOrgCode(Long prodCode, Long orgCode) {
		String sql = "DELETE FROM product001 WHERE prodcode = :prodCode AND orgcode = :orgCode";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("prodCode", prodCode).addValue("orgCode",
				orgCode);
		jdbcTemplate.update(sql, params);
	}

	public List<com.bbots.mfin.dto.ProductDto> findAllProducts() {

		String sql = "SELECT * FROM product001";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {

			com.bbots.mfin.dto.ProductDto dto = new com.bbots.mfin.dto.ProductDto();

			dto.setOrgcode(rs.getLong("orgcode"));
			dto.setProdcode(rs.getLong("prodcode"));
			dto.setProdname(rs.getString("prodname"));
			dto.setHomeUrl(rs.getString("home_url"));
			dto.setStatus(rs.getBoolean("status"));

			dto.setEuser(rs.getString("euser"));
			dto.setAuser(rs.getString("auser"));
			dto.setCuser(rs.getString("cuser"));

			dto.setLogo(rs.getString("logo"));

			Timestamp edateTs = rs.getTimestamp("edate");
			if (edateTs != null) {
				dto.setEdate(edateTs.toLocalDateTime());
			}

			Timestamp adateTs = rs.getTimestamp("adate");
			if (adateTs != null) {
				dto.setAdate(adateTs.toLocalDateTime());
			}

			Timestamp cdateTs = rs.getTimestamp("cdate");
			if (cdateTs != null) {
				dto.setCdate(cdateTs.toLocalDateTime());
			}

			return dto;
		});
	}

	public List<com.bbots.mfin.dto.ProductDto> findByOrgCode(Long orgCode) {
		String sql = "SELECT * FROM product002 WHERE orgcode = :orgCode";
		Map<String, Object> params = new HashMap<>();
		params.put("orgCode", orgCode);
		return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
			com.bbots.mfin.dto.ProductDto dto = new com.bbots.mfin.dto.ProductDto();
			dto.setOrgcode(rs.getLong("orgcode"));
			dto.setProdcode(rs.getLong("prodcode"));
			dto.setStatus(rs.getBoolean("status"));
			dto.setEuser(rs.getString("euser"));
			dto.setAuser(rs.getString("auser"));
			dto.setCuser(rs.getString("cuser"));
			Timestamp edateTs = rs.getTimestamp("edate");
			if (edateTs != null) {
				dto.setEdate(edateTs.toLocalDateTime());
			}

			Timestamp adateTs = rs.getTimestamp("adate");
			if (adateTs != null) {
				dto.setAdate(adateTs.toLocalDateTime());
			}

			Timestamp cdateTs = rs.getTimestamp("cdate");
			if (cdateTs != null) {
				dto.setCdate(cdateTs.toLocalDateTime());
			}
			return dto;
		});
	}

	public void upsertProduct(com.bbots.mfin.dto.ProductDto product) {
		boolean auth = false;
		LocalDateTime now = LocalDateTime.now();
		String auser = null;
		Timestamp adate = null;
		if (!auth) {
			auser = product.getAuser();
			adate = Timestamp.valueOf(now);
		}
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orgcode", product.getOrgcode());
		params.addValue("prodcode", product.getProdcode());
		params.addValue("prodname", product.getProdname());
		params.addValue("homeUrl", product.getHomeUrl());
		params.addValue("status", product.getStatus());
		params.addValue("euser", product.getEuser());
		params.addValue("auser", auser);
		params.addValue("adate", adate);
		params.addValue("cuser", product.getCuser());
		params.addValue("logo", product.getLogo());

		if (existsByOrgCodeAndProdCode(product.getOrgcode(), product.getProdcode())) {
			String sql = "UPDATE product001 SET prodname = :prodname, home_url = :homeUrl, status = :status, logo = :logo, euser = :euser, edate = CURRENT_TIMESTAMP, auser = :auser, adate = :adate WHERE orgcode = :orgcode AND prodcode = :prodcode";
			jdbcTemplate.update(sql, params);
		} else {
			String sql = "INSERT INTO product001 (orgcode, prodcode, prodname, home_url, status, euser, edate, auser, adate, cuser, cdate, logo) VALUES (:orgcode, :prodcode, :prodname, :homeUrl, :status, NULL, NULL, :auser, :adate, :cuser, CURRENT_TIMESTAMP, :logo)";
			jdbcTemplate.update(sql, params);
		}
	}

	public boolean existsInProduct002(Long orgCode, Long prodCode) {
		String sql = "SELECT COUNT(*) FROM product002 WHERE orgcode = :orgCode AND prodcode = :prodCode";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("orgCode", orgCode)
				.addValue("prodCode", prodCode);
		Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return count != null && count > 0;
	}

	public void upsertMappedProduct(ProductMappedDto dto) {
		boolean auth = false;
		LocalDateTime now = LocalDateTime.now();
		String auser = null;
		Timestamp adate = null;
		if (!auth) {
			auser = dto.getAuser();
			adate = Timestamp.valueOf(now);
		}

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orgcode", dto.getOrgcode());
		params.addValue("prodcode", dto.getProdcode());
		params.addValue("status", dto.getStatus());
		params.addValue("euser", dto.getEuser());
		params.addValue("auser", auser);
		params.addValue("adate", adate);
		params.addValue("cuser", dto.getCuser());

		if (existsInProduct002(dto.getOrgcode(), dto.getProdcode())) {
			String sql = "UPDATE product002 SET status = :status, euser = :euser, edate = CURRENT_TIMESTAMP, auser = :auser, adate = :adate WHERE orgcode = :orgcode AND prodcode = :prodcode";
			jdbcTemplate.update(sql, params);
		} else {
			String sql = "INSERT INTO product002 (orgcode, prodcode, status, euser, edate, auser, adate, cuser, cdate) VALUES (:orgcode, :prodcode, :status, NULL, NULL, :auser, :adate, :cuser, CURRENT_TIMESTAMP)";
			jdbcTemplate.update(sql, params);
		}
	}
}
