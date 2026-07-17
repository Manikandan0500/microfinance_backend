package com.bbots.mfin.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bbots.mfin.dto.LoginHistoryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class LoginHistoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginHistoryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<LoginHistoryDto> ROW_MAPPER = (rs, rowNum) -> {
        LoginHistoryDto dto = new LoginHistoryDto();
        dto.setLoginId(rs.getLong("login_id"));
        dto.setOrgcode(rs.getLong("orgcode"));
        dto.setProdcode(rs.getObject("prodcode") != null ? rs.getLong("prodcode") : null);
        dto.setUsercd(rs.getString("userscd"));
        dto.setUserName(rs.getString("user_name"));
        dto.setLoginTime(rs.getTimestamp("login_time") != null
                ? rs.getTimestamp("login_time").toLocalDateTime()
                : null);
        dto.setLogoutTime(rs.getTimestamp("logout_time") != null
                ? rs.getTimestamp("logout_time").toLocalDateTime()
                : null);
        dto.setLoginStatus(rs.getObject("login_status") != null ? rs.getInt("login_status") : null);
        dto.setFailureReason(rs.getString("failure_reason"));
        dto.setIpAddress(rs.getString("ip_address"));
        dto.setDeviceInfo(rs.getString("device_info"));
        dto.setDeviceId(rs.getString("device_id"));
        dto.setSessionId(rs.getString("session_id"));
        dto.setGeoLocation(rs.getString("geo_location"));
        return dto;
    };

    private static final String SELECT_WITH_NAME = "SELECT h.*, CONCAT(u.fname, ' ', u.lname) AS user_name " +
            "FROM user007 h " +
            "LEFT JOIN user001 u " +
            "ON u.orgcode = h.orgcode AND u.userscd = h.userscd ";

    public void save(LoginHistoryDto dto) {
        String sql = "INSERT INTO loandev.user007 " +
                "(orgcode, prodcode, userscd, login_time, logout_time, login_status, " +
                " failure_reason, ip_address, device_info, device_id, session_id, geo_location, emailid) " +
                "VALUES (:orgcode, :prodcode, :userscd, :loginTime, :logoutTime, :loginStatus, " +
                " :failureReason, :ipAddress, :deviceInfo, :deviceId, :sessionId, :geoLocation ,:emailId)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", dto.getOrgcode())
                .addValue("prodcode", dto.getProdcode())
                .addValue("userscd", dto.getUsercd())
                .addValue("loginTime", dto.getLoginTime())
                .addValue("logoutTime", dto.getLogoutTime())
                .addValue("loginStatus", dto.getLoginStatus())
                .addValue("failureReason", dto.getFailureReason())
                .addValue("ipAddress", dto.getIpAddress())
                .addValue("deviceInfo", dto.getDeviceInfo())
                .addValue("deviceId", dto.getDeviceId())
                .addValue("sessionId", dto.getSessionId())
                .addValue("geoLocation", dto.getGeoLocation())
                .addValue("emailId", dto.getEmailId());

        jdbcTemplate.update(sql, params);
    }

    public List<LoginHistoryDto> findAllOrderByLoginTimeDesc() {
        String sql = SELECT_WITH_NAME +
                "ORDER BY h.login_time DESC";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public List<LoginHistoryDto> findByOrgcodeOrderByLoginTimeDesc(Long orgcode) {
        String sql = SELECT_WITH_NAME +
                "WHERE h.orgcode = :orgcode " +
                "ORDER BY h.login_time DESC";
        return jdbcTemplate.query(sql, new MapSqlParameterSource("orgcode", orgcode), ROW_MAPPER);
    }

    public List<LoginHistoryDto> findByOrgcodeAndProdcodeOrderByLoginTimeDesc(Long orgcode, Long prodcode) {
        String sql = SELECT_WITH_NAME +
                "WHERE h.orgcode = :orgcode AND h.prodcode = :prodcode " +
                "ORDER BY h.login_time DESC";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", orgcode)
                .addValue("prodcode", prodcode);
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public List<LoginHistoryDto> findByOrgcodeAndUsercdOrderByLoginTimeDesc(Long orgcode, String userscd) {
        String sql = SELECT_WITH_NAME +
                "WHERE h.orgcode = :orgcode AND h.userscd = :userscd " +
                "ORDER BY h.login_time DESC";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", orgcode)
                .addValue("userscd", userscd);
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public List<LoginHistoryDto> findByOrgcodeAndUsercdAndProdcodeOrderByLoginTimeDesc(
            Long orgcode, String userscd, Long prodcode) {
        String sql = SELECT_WITH_NAME +
                "WHERE h.orgcode = :orgcode AND h.userscd = :userscd AND h.prodcode = :prodcode " +
                "ORDER BY h.login_time DESC";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", orgcode)
                .addValue("userscd", userscd)
                .addValue("prodcode", prodcode);
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public LoginHistoryDto findLastSuccessfulLogin(Long orgcode, String userscd) {
        String sql = SELECT_WITH_NAME +
                "WHERE h.orgcode = :orgcode AND h.userscd = :userscd AND h.login_status = 1 " +
                "ORDER BY h.login_time DESC LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgcode", orgcode)
                .addValue("userscd", userscd);
        try {
            return jdbcTemplate.queryForObject(sql, params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateLogoutTimeBySessionId(String sessionId) {
        String sql = "UPDATE user007 SET logout_time = :logoutTime " +
                "WHERE session_id = :sessionId AND logout_time IS NULL";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("logoutTime", LocalDateTime.now())
                .addValue("sessionId", sessionId);
        jdbcTemplate.update(sql, params);
    }

    public void updateLogoutTimeByUserAndOrg(String userscd, Long orgcode) {
        String sql = "UPDATE user007 SET logout_time = :logoutTime " +
                "WHERE login_id = (" +
                "  SELECT login_id FROM user007 " +
                "  WHERE userscd = :userscd AND orgcode = :orgcode " +
                "  AND logout_time IS NULL AND login_status = 1 " +
                "  ORDER BY login_time DESC LIMIT 1" +
                ")";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("logoutTime", LocalDateTime.now())
                .addValue("userscd", userscd)
                .addValue("orgcode", orgcode);
        jdbcTemplate.update(sql, params);
    }

    public Map<String, Object> findPaginated(
            Long orgCode, String usercd, Long prodcode, Integer status,
            LocalDateTime startDateTime, LocalDateTime endDateTime,
            int offset, int limit, String search) {

        StringBuilder baseSql = new StringBuilder(SELECT_WITH_NAME + "WHERE 1=1");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (orgCode != null) {
            baseSql.append(" AND h.orgcode = :orgcode");
            params.addValue("orgcode", orgCode);
        }
        if (usercd != null && !usercd.trim().isEmpty()) {
            baseSql.append(" AND h.userscd = :usercd");
            params.addValue("usercd", usercd);
        }
        if (prodcode != null) {
            baseSql.append(" AND h.prodcode = :prodcode");
            params.addValue("prodcode", prodcode);
        }
        if (status != null) {
            baseSql.append(" AND h.login_status = :status");
            params.addValue("status", status);
        }
        if (startDateTime != null) {
            baseSql.append(" AND h.login_time >= :startDateTime");
            params.addValue("startDateTime", startDateTime);
        }
        if (endDateTime != null) {
            baseSql.append(" AND h.login_time <= :endDateTime");
            params.addValue("endDateTime", endDateTime);
        }
        if (search != null && !search.trim().isEmpty()) {
            baseSql.append(" AND (h.ip_address ILIKE :search OR h.geo_location ILIKE :search OR h.failure_reason ILIKE :search OR CONCAT(u.fname, ' ', u.lname) ILIKE :search)");
            params.addValue("search", "%" + search.trim() + "%");
        }

        String countSql = "SELECT COUNT(*) AS total, " +
                "SUM(CASE WHEN h.login_status = 1 THEN 1 ELSE 0 END) AS success, " +
                "SUM(CASE WHEN h.login_status = 0 THEN 1 ELSE 0 END) AS failed " +
                "FROM user007 h " +
                "LEFT JOIN user001 u ON u.orgcode = h.orgcode AND u.userscd = h.userscd " +
                "WHERE 1=1";

        // Reconstruct count SQL with filters
        StringBuilder countFilteredSql = new StringBuilder(countSql);
        if (orgCode != null) countFilteredSql.append(" AND h.orgcode = :orgcode");
        if (usercd != null && !usercd.trim().isEmpty()) countFilteredSql.append(" AND h.userscd = :usercd");
        if (prodcode != null) countFilteredSql.append(" AND h.prodcode = :prodcode");
        if (status != null) countFilteredSql.append(" AND h.login_status = :status");
        if (startDateTime != null) countFilteredSql.append(" AND h.login_time >= :startDateTime");
        if (endDateTime != null) countFilteredSql.append(" AND h.login_time <= :endDateTime");
        if (search != null && !search.trim().isEmpty()) {
            countFilteredSql.append(" AND (h.ip_address ILIKE :search OR h.geo_location ILIKE :search OR h.failure_reason ILIKE :search OR CONCAT(u.fname, ' ', u.lname) ILIKE :search)");
        }

        Map<String, Object> countsMap = jdbcTemplate.queryForMap(countFilteredSql.toString(), params);
        Long totalElements = ((Number) countsMap.getOrDefault("total", 0L)).longValue();
        Long successCount = countsMap.get("success") != null ? ((Number) countsMap.get("success")).longValue() : 0L;
        Long failedCount = countsMap.get("failed") != null ? ((Number) countsMap.get("failed")).longValue() : 0L;

        StringBuilder paginatedSql = new StringBuilder(baseSql);
        paginatedSql.append(" ORDER BY h.login_time DESC LIMIT :limit OFFSET :offset");
        params.addValue("limit", limit);
        params.addValue("offset", offset);

        List<LoginHistoryDto> content = jdbcTemplate.query(paginatedSql.toString(), params, ROW_MAPPER);

        Map<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("totalElements", totalElements);
        result.put("successCount", successCount);
        result.put("failedCount", failedCount);

        return result;
    }
}