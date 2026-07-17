package com.bbots.mfin.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.SmtpMailConfigDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class SmtpMailConfigRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SmtpMailConfigRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SmtpMailConfigDto> findAll() {
        String sql = "SELECT orgcode, provider, hostname, smtpport, encryption, username, passhash, fromid, "
                + "displayname, replytoid, euser, edate, auser, adate, cuser, cdate "
                + "FROM org002 ORDER BY orgcode";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    public Optional<SmtpMailConfigDto> findById(Long orgcode) {
        String sql = "SELECT orgcode, provider, hostname, smtpport, encryption, username, passhash, fromid, "
                + "displayname, replytoid, euser, edate, auser, adate, cuser, cdate "
                + "FROM org002 WHERE orgcode = :orgcode";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", orgcode);

        try {
            SmtpMailConfigDto dto = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> mapRow(rs));
            return Optional.ofNullable(dto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existsById(Long orgcode) {
        String sql = "SELECT COUNT(*) FROM org002 WHERE orgcode = :orgcode";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", orgcode);

        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    public SmtpMailConfigDto save(SmtpMailConfigDto dto) {
        String sql = "INSERT INTO org002 (orgcode, provider, hostname, smtpport, encryption, username, passhash, "
                + "fromid, displayname, replytoid, euser, edate, auser, adate, cuser, cdate) "
                + "VALUES (:orgcode, :provider, :hostname, :smtpport, :encryption, :username, :passhash, "
                + ":fromid, :displayname, :replytoid, :euser, :edate, :auser, :adate, :cuser, :cdate)";

        jdbcTemplate.update(sql, buildParams(dto, false));
        return findById(dto.getOrgcode()).orElse(dto);
    }

    public SmtpMailConfigDto update(Long orgcode, SmtpMailConfigDto dto) {
        dto.setOrgcode(orgcode);

        String sql = "UPDATE org002 SET provider = :provider, hostname = :hostname, smtpport = :smtpport, "
                + "encryption = :encryption, username = :username, passhash = :passhash, fromid = :fromid, "
                + "displayname = :displayname, replytoid = :replytoid, euser = :euser, edate = :edate, "
                + "auser = :auser, adate = :adate, cuser = :cuser, cdate = :cdate "
                + "WHERE orgcode = :orgcode";

        jdbcTemplate.update(sql, buildParams(dto, true));
        return findById(orgcode).orElse(dto);
    }

    public void deleteById(Long orgcode) {
        String sql = "DELETE FROM org002 WHERE orgcode = :orgcode";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", orgcode);
        jdbcTemplate.update(sql, params);
    }

    private MapSqlParameterSource buildParams(SmtpMailConfigDto dto, boolean isUpdate) {
        LocalDateTime effectiveEdate = isUpdate
                ? (dto.getEdate() != null ? dto.getEdate() : LocalDateTime.now())
                : dto.getEdate();

        return new MapSqlParameterSource()
                .addValue("orgcode", dto.getOrgcode())
                .addValue("provider", dto.getProvider())
                .addValue("hostname", dto.getHostname())
                .addValue("smtpport", dto.getSmtpport())
                .addValue("encryption", dto.getEncryption())
                .addValue("username", dto.getUsername())
                .addValue("passhash", dto.getPasshash())
                .addValue("fromid", dto.getFromid())
                .addValue("displayname", dto.getDisplayname())
                .addValue("replytoid", dto.getReplytoid())
                .addValue("euser", dto.getEuser())
                .addValue("edate", toTimestamp(effectiveEdate))
                .addValue("auser", dto.getAuser())
                .addValue("adate", toTimestamp(dto.getAdate()))
                .addValue("cuser", dto.getCuser())
                .addValue("cdate", toTimestamp(dto.getCdate()));
    }

    private SmtpMailConfigDto mapRow(ResultSet rs) throws SQLException {
        SmtpMailConfigDto dto = new SmtpMailConfigDto();
        dto.setOrgcode(rs.getLong("orgcode"));
        dto.setProvider(rs.getString("provider"));
        dto.setHostname(rs.getString("hostname"));
        dto.setSmtpport(rs.getLong("smtpport"));
        dto.setEncryption(rs.getString("encryption"));
        dto.setUsername(rs.getString("username"));
        dto.setPasshash(rs.getString("passhash"));
        dto.setFromid(rs.getString("fromid"));
        dto.setDisplayname(rs.getString("displayname"));
        dto.setReplytoid(rs.getString("replytoid"));
        dto.setEuser(rs.getString("euser"));
        dto.setEdate(toLocalDateTime(rs.getTimestamp("edate")));
        dto.setAuser(rs.getString("auser"));
        dto.setAdate(toLocalDateTime(rs.getTimestamp("adate")));
        dto.setCuser(rs.getString("cuser"));
        dto.setCdate(toLocalDateTime(rs.getTimestamp("cdate")));
        return dto;
    }

    private Timestamp toTimestamp(LocalDateTime value) {
        return value != null ? Timestamp.valueOf(value) : null;
    }

    private LocalDateTime toLocalDateTime(Timestamp value) {
        return value != null ? value.toLocalDateTime() : null;
    }
}
