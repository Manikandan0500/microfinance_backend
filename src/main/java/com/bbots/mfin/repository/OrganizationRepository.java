package com.bbots.mfin.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.OrganizationDto;

@Repository
public class OrganizationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrganizationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<OrganizationDto> findByOrgCode(Long orgCode) {

        String sql = "SELECT " +
                "orgcode, name, opendate, logo, country, divisionname, pincode, " +
                "addrline1, addrline2, addrline3, addrline4, addrline5, " +
                "telephone, email, status, indiv, " +
                "euser, edate, auser, adate, cuser, cdate " +
                "FROM org001 " +
                "WHERE orgcode = :orgCode";

        MapSqlParameterSource params = new MapSqlParameterSource("orgCode", orgCode);

        try {

            OrganizationDto dto = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {

                OrganizationDto item = new OrganizationDto();

                item.setOrgCode(rs.getLong("orgcode"));
                item.setOrgName(rs.getString("name"));

                Date openDate = rs.getDate("opendate");
                item.setOpenDate(
                        openDate != null
                                ? openDate.toLocalDate()
                                : null);

                item.setLogo(rs.getString("logo"));

                item.setCountry(rs.getString("country"));
                item.setDivisionName(rs.getString("divisionname"));
                item.setPincode(rs.getString("pincode"));

                item.setAddressLine1(rs.getString("addrline1"));
                item.setAddressLine2(rs.getString("addrline2"));
                item.setAddressLine3(rs.getString("addrline3"));
                item.setAddressLine4(rs.getString("addrline4"));
                item.setAddressLine5(rs.getString("addrline5"));

                item.setTelephone(rs.getString("telephone"));
                item.setEmail(rs.getString("email"));

                item.setStatus(rs.getInt("status"));

                item.setIndiv(rs.getInt("indiv"));

                item.setEuser(rs.getString("euser"));

                Timestamp edate = rs.getTimestamp("edate");
                item.setEdate(
                        edate != null
                                ? edate.toLocalDateTime()
                                : null);

                item.setAuser(rs.getString("auser"));

                Timestamp adate = rs.getTimestamp("adate");
                item.setAdate(
                        adate != null
                                ? adate.toLocalDateTime()
                                : null);

                item.setCuser(rs.getString("cuser"));

                Timestamp cdate = rs.getTimestamp("cdate");
                item.setCdate(
                        cdate != null
                                ? cdate.toLocalDateTime()
                                : null);

                return item;
            });

            return Optional.ofNullable(dto);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<OrganizationDto> findAll() {
        String sql = "SELECT orgcode, name, opendate, country, divisionname, pincode, " +
                "addrline1, addrline2, addrline3, addrline4, addrline5, " +
                "telephone, email, status, indiv, " +
                "euser, edate, auser, adate, cuser, cdate "+
                "FROM org001 ORDER BY orgcode";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            OrganizationDto dto = new OrganizationDto();

            dto.setOrgCode(rs.getLong("orgcode"));
            dto.setOrgName(rs.getString("name"));
            dto.setOpenDate(rs.getDate("opendate") != null ? rs.getDate("opendate").toLocalDate() : null);
            dto.setCountry(rs.getString("country"));
            dto.setDivisionName(rs.getString("divisionname"));
            dto.setPincode(rs.getString("pincode"));
            dto.setAddressLine1(rs.getString("addrline1"));
            dto.setAddressLine2(rs.getString("addrline2"));
            dto.setAddressLine3(rs.getString("addrline3"));
            dto.setAddressLine4(rs.getString("addrline4"));
            dto.setAddressLine5(rs.getString("addrline5"));
            dto.setTelephone(rs.getString("telephone"));
            dto.setEmail(rs.getString("email"));
            dto.setStatus(rs.getInt("status"));
            dto.setIndiv(rs.getInt("indiv"));
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
    }

    public Optional<OrganizationDto> findByOrgName(String orgName) {
        String sql = "SELECT orgcode, name, opendate, country, divisionname, pincode, " +
                "addrline1, addrline2, addrline3, addrline4, addrline5, " +
                "telephone, email, status, indiv " +
                "FROM org001 WHERE name = :orgName";
        MapSqlParameterSource params = new MapSqlParameterSource("orgName", orgName);

        try {
            OrganizationDto dto = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                OrganizationDto item = new OrganizationDto();
                item.setOrgCode(rs.getLong("orgcode"));
                item.setOrgName(rs.getString("name"));
                item.setOpenDate(rs.getDate("opendate") != null ? rs.getDate("opendate").toLocalDate() : null);
                item.setCountry(rs.getString("country"));
                item.setDivisionName(rs.getString("divisionname"));
                item.setPincode(rs.getString("pincode"));
                item.setAddressLine1(rs.getString("addrline1"));
                item.setAddressLine2(rs.getString("addrline2"));
                item.setAddressLine3(rs.getString("addrline3"));
                item.setAddressLine4(rs.getString("addrline4"));
                item.setAddressLine5(rs.getString("addrline5"));
                item.setTelephone(rs.getString("telephone"));
                item.setEmail(rs.getString("email"));
                item.setStatus(rs.getInt("status"));
                item.setIndiv(rs.getInt("indiv"));
                return item;
            });
            return Optional.ofNullable(dto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public OrganizationDto save(OrganizationDto organization) {
        if (organization.getOrgCode() == null) {
            String maxSql = "SELECT COALESCE(MAX(orgcode), 100) FROM org001";
            Long maxCode = jdbcTemplate.queryForObject(maxSql, new MapSqlParameterSource(), Long.class);
            organization.setOrgCode(maxCode + 1);
        }

        String insertSql = "INSERT INTO org001 (" +
                "orgcode, name, opendate, country, divisionname, pincode, " +
                "addrline1, addrline2, addrline3, addrline4, addrline5, " +
                "telephone, email, status, indiv, cuser, cdate, auser , adate" +
                ") VALUES (" +
                ":orgcode, :name, :opendate, :country, :divisionname, :pincode, " +
                ":addrline1, :addrline2, :addrline3, :addrline4, :addrline5, " +
                ":telephone, :email, :status, :indiv, :cuser, :cdate, :auser , :adate" +
                ")";

        Boolean auth = false;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orgcode", organization.getOrgCode());
        params.addValue("name", organization.getOrgName());
        params.addValue("opendate", organization.getOpenDate());
        params.addValue("country", organization.getCountry());
        params.addValue("divisionname", organization.getDivisionName());
        params.addValue("pincode", organization.getPincode());
        params.addValue("addrline1", organization.getAddressLine1());
        params.addValue("addrline2", organization.getAddressLine2());
        params.addValue("addrline3", organization.getAddressLine3());
        params.addValue("addrline4", organization.getAddressLine4());
        params.addValue("addrline5", organization.getAddressLine5());
        params.addValue("telephone", organization.getTelephone());
        params.addValue("email", organization.getEmail());
        params.addValue("status", organization.getStatus());
        params.addValue("indiv", organization.getIndiv());
        params.addValue("cuser", organization.getUserName());
        LocalDateTime date = LocalDateTime.now();
        params.addValue("cdate", date);
        if (!auth) {
            params.addValue("auser", organization.getUserName());
            params.addValue("adate", date);
        } else {
            params.addValue("auser", null);
            params.addValue("adate", null);
        }

        jdbcTemplate.update(insertSql, params);

        String selectSql = "SELECT orgcode, name, opendate, country, divisionname, pincode, " +
                "addrline1, addrline2, addrline3, addrline4, addrline5, " +
                "telephone, email, status, indiv " +
                "FROM org001 WHERE orgcode = :orgcode";

        return jdbcTemplate.queryForObject(
                selectSql,
                new MapSqlParameterSource("orgcode", organization.getOrgCode()),
                (rs, rowNum) -> {
                    OrganizationDto dto = new OrganizationDto();
                    dto.setOrgCode(rs.getLong("orgcode"));
                    dto.setOrgName(rs.getString("name"));
                    dto.setOpenDate(rs.getDate("opendate") != null ? rs.getDate("opendate").toLocalDate() : null);
                    dto.setCountry(rs.getString("country"));
                    dto.setDivisionName(rs.getString("divisionname"));
                    dto.setPincode(rs.getString("pincode"));
                    dto.setAddressLine1(rs.getString("addrline1"));
                    dto.setAddressLine2(rs.getString("addrline2"));
                    dto.setAddressLine3(rs.getString("addrline3"));
                    dto.setAddressLine4(rs.getString("addrline4"));
                    dto.setAddressLine5(rs.getString("addrline5"));
                    dto.setTelephone(rs.getString("telephone"));
                    dto.setEmail(rs.getString("email"));
                    dto.setStatus(rs.getInt("status"));
                    dto.setIndiv(rs.getInt("indiv"));
                    return dto;
                });
    }

    public int update(OrganizationDto organization) {

        String sql = "UPDATE org001 SET " +
                "name = :name, " +
                "opendate = :opendate, " +
                "country = :country, " +
                "divisionname = :divisionname, " +
                "pincode = :pincode, " +
                "addrline1 = :addrline1, " +
                "addrline2 = :addrline2, " +
                "addrline3 = :addrline3, " +
                "addrline4 = :addrline4, " +
                "addrline5 = :addrline5, " +
                "telephone = :telephone, " +
                "email = :email, " +
                "status = :status, " +
                "indiv = :indiv, " +
                "edate = :edate, " +
                "euser = :euser, " +
                "auser = :auser, " +
                "adate = :adate " +
                "WHERE orgcode = :orgcode";
        Boolean auth = false;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orgcode", organization.getOrgCode());
        params.addValue("name", organization.getOrgName());
        params.addValue("opendate", organization.getOpenDate());
        params.addValue("country", organization.getCountry());
        params.addValue("divisionname", organization.getDivisionName());
        params.addValue("pincode", organization.getPincode());
        params.addValue("addrline1", organization.getAddressLine1());
        params.addValue("addrline2", organization.getAddressLine2());
        params.addValue("addrline3", organization.getAddressLine3());
        params.addValue("addrline4", organization.getAddressLine4());
        params.addValue("addrline5", organization.getAddressLine5());
        params.addValue("telephone", organization.getTelephone());
        params.addValue("email", organization.getEmail());
        params.addValue("status", organization.getStatus());
        params.addValue("indiv", organization.getIndiv());
        params.addValue("euser", organization.getUserName());
        LocalDateTime date = LocalDateTime.now();
        params.addValue("edate", date);
        if (!auth) {
            params.addValue("auser", organization.getUserName());
            params.addValue("adate", date);
        } else {
            params.addValue("auser", null);
            params.addValue("adate", null);
        }

        return jdbcTemplate.update(sql, params);
    }

    public void deleteByOrgCode(Long orgCode) {
        String sql = "DELETE FROM org001 WHERE orgcode = :orgCode";
        MapSqlParameterSource params = new MapSqlParameterSource("orgCode", orgCode);
        jdbcTemplate.update(sql, params);
    }

    public boolean existsByOrgCode(Long long1) {
        String sql = "SELECT COUNT(*) FROM org001 WHERE orgcode = :orgCode";
        MapSqlParameterSource params = new MapSqlParameterSource("orgCode", long1);

        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
