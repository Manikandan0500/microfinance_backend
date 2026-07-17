package com.bbots.mfin.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.VerifyDto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class VerifyRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VerifyRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ── Save new OTP record ───────────────────────────────────────────────────
    public void save(VerifyDto dto) {
        String sql =
            "INSERT INTO VERIFY001 " +
            "(orgcode, prodcode, gendate, verifycode, pgmid, reqby, " +
            " gentime, otphash, expiretime, transportmode, tokenkey, vresult, retrycnt, blockeduntil) " +
            "VALUES " +
            "(:orgcode, :prodcode, :gendate, :verifycode, :pgmid, :reqby, " +
            " :gentime, :otphash, :expiretime, :transportmode, :tokenkey, 0, 0, NULL)";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("orgcode",       dto.getOrgcode())
            .addValue("prodcode",      dto.getProdcode())
            .addValue("gendate",       Date.valueOf(dto.getGendate()))
            .addValue("verifycode",    dto.getVerifycode())
            .addValue("pgmid",         dto.getPgmid())
            .addValue("reqby",         dto.getReqby())
            .addValue("gentime",       Timestamp.valueOf(dto.getGentime()))
            .addValue("otphash",       dto.getOtphash())
            .addValue("expiretime",    Timestamp.valueOf(dto.getExpiretime()))
            .addValue("transportmode", dto.getTransportmode())
            .addValue("tokenkey",      dto.getTokenkey());

        jdbcTemplate.update(sql, params);
    }

    // ── Find by tokenKey ──────────────────────────────────────────────────────
    public Optional<VerifyDto> findByTokenKey(String tokenKey) {
        String sql =
            "SELECT orgcode, prodcode, gendate, verifycode, pgmid, reqby, " +
            "       gentime, otphash, expiretime, transportmode, " +
            "       tokenkey, vresult, retrycnt, blockeduntil " +
            "FROM VERIFY001 WHERE tokenkey = :tokenKey";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("tokenKey", tokenKey);

        return jdbcTemplate.query(sql, params, rs -> {
            if (rs.next()) {
                VerifyDto dto = new VerifyDto();
                dto.setOrgcode(rs.getLong("orgcode"));
                dto.setProdcode(rs.getLong("prodcode"));
                dto.setGendate(rs.getDate("gendate").toLocalDate());
                dto.setVerifycode(rs.getLong("verifycode"));
                dto.setPgmid(rs.getString("pgmid"));
                dto.setReqby(rs.getString("reqby"));
                dto.setGentime(rs.getTimestamp("gentime").toLocalDateTime());
                dto.setOtphash(rs.getString("otphash"));
                dto.setExpiretime(rs.getTimestamp("expiretime").toLocalDateTime());
                dto.setTransportmode(rs.getString("transportmode"));
                dto.setTokenkey(rs.getString("tokenkey"));
                dto.setVresult(rs.getInt("vresult"));
                dto.setRetrycnt(rs.getInt("retrycnt"));

                // ── blockeduntil (nullable) ───────────────────────────────
                Timestamp blocked = rs.getTimestamp("blockeduntil");
                dto.setBlockeduntil(blocked != null ? blocked.toLocalDateTime() : null);

                return Optional.of(dto);
            }
            return Optional.empty();
        });
    }

    // ── Update Vresult (0=NotVerified, 1=Success, 2=Failed/Used) ─────────────
    public void updateVresult(String tokenKey, int vresult) {
        String sql =
            "UPDATE VERIFY001 SET vresult = :vresult " +
            "WHERE tokenkey = :tokenKey";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("vresult",  vresult)
            .addValue("tokenKey", tokenKey);

        jdbcTemplate.update(sql, params);
    }

    // ── Increment retry count ─────────────────────────────────────────────────
    public void incrementRetry(String tokenKey) {
        String sql =
            "UPDATE VERIFY001 SET retrycnt = retrycnt + 1 " +
            "WHERE tokenkey = :tokenKey";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("tokenKey", tokenKey);

        jdbcTemplate.update(sql, params);
    }

    // ── Block user: set blockeduntil timestamp ────────────────────────────────
    public void blockUntil(String tokenKey, LocalDateTime blockedUntil) {
        String sql =
            "UPDATE VERIFY001 SET blockeduntil = :blockeduntil " +
            "WHERE tokenkey = :tokenKey";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("blockeduntil", Timestamp.valueOf(blockedUntil))  // java.sql.Timestamp used here only
            .addValue("tokenKey",     tokenKey);

        jdbcTemplate.update(sql, params);
    }
    // ── Reset retry count and clear block (called when block expires) ─────────
    public void resetRetryAndBlock(String tokenKey) {
        String sql =
            "UPDATE VERIFY001 SET retrycnt = 0, blockeduntil = NULL " +
            "WHERE tokenkey = :tokenKey";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("tokenKey", tokenKey);

        jdbcTemplate.update(sql, params);
    }
    
 // ── Find latest record by email (reqby) ──────────────────────────────────
    public Optional<VerifyDto> findLatestByEmail(String email) {
        String sql =
            "SELECT orgcode, prodcode, gendate, verifycode, pgmid, reqby, " +
            "       gentime, otphash, expiretime, transportmode, " +
            "       tokenkey, vresult, retrycnt, blockeduntil " +
            "FROM VERIFY001 WHERE reqby = :email " +
            "ORDER BY gentime DESC LIMIT 1";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("email", email);

        return jdbcTemplate.query(sql, params, rs -> {
            if (rs.next()) {
                VerifyDto dto = new VerifyDto();
                dto.setOrgcode(rs.getLong("orgcode"));
                dto.setProdcode(rs.getLong("prodcode"));
                dto.setGendate(rs.getDate("gendate").toLocalDate());
                dto.setVerifycode(rs.getLong("verifycode"));
                dto.setPgmid(rs.getString("pgmid"));
                dto.setReqby(rs.getString("reqby"));
                dto.setGentime(rs.getTimestamp("gentime").toLocalDateTime());
                dto.setOtphash(rs.getString("otphash"));
                dto.setExpiretime(rs.getTimestamp("expiretime").toLocalDateTime());
                dto.setTransportmode(rs.getString("transportmode"));
                dto.setTokenkey(rs.getString("tokenkey"));
                dto.setVresult(rs.getInt("vresult"));
                dto.setRetrycnt(rs.getInt("retrycnt"));
                Timestamp blocked = rs.getTimestamp("blockeduntil");
                dto.setBlockeduntil(blocked != null ? blocked.toLocalDateTime() : null);
                return Optional.of(dto);
            }
            return Optional.empty();
        });
    }
}