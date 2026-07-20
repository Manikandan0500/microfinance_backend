package com.bbots.mfin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.LoggedInUserContext;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthorizationProcedureService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoggedInUserContext loggedInUserContext;

    /**
     * Calls the pr_handle_authorization PostgreSQL Procedure.
     * 
     * @param orgCode     Organization code (e.g. "ORG01" or "101")
     * @param programId   Program ID (e.g. "USR-CRT", "ROLE-CRT")
     * @param tableName   Target table name (e.g. "USERS001", "ACCESS001")
     * @param payload     The DTO / Entity to be serialized to JSON
     */
    public void processAuthorization(Long orgCode, String programId, String tableName, Object payload, String action) {
        String sql = "CALL pr_handle_authorization(?::bigint, ?::varchar, ?::varchar, ?::varchar, ?::json, ?::varchar)";

        try {
            // Convert to Map and flatten nested objects (e.g. "id") so that
            // PostgreSQL's json_populate_record can map all keys to flat column names.

            java.util.Map<String, Object> map = objectMapper.convertValue(payload, new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {});
            java.util.Map<String, Object> lowerCaseMap = new java.util.HashMap<>();
            if (map != null) {
                for (java.util.Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey().toLowerCase();
                    Object value = entry.getValue();
                    // Flatten nested maps (e.g. "id": {"orgCode":101,"programId":"X"})
                    // into the top-level map with lowercased keys
                    if (value instanceof java.util.Map) {
                        @SuppressWarnings("unchecked")
                        java.util.Map<String, Object> nested = (java.util.Map<String, Object>) value;
                        for (java.util.Map.Entry<String, Object> nestedEntry : nested.entrySet()) {
                            lowerCaseMap.put(nestedEntry.getKey().toLowerCase(), nestedEntry.getValue());
                        }
                        // Also keep the original key in case it's needed elsewhere
                        lowerCaseMap.put(key, value);
                    } else {
                        lowerCaseMap.put(key, value);
                    }
                }
            }
            String userName = loggedInUserContext.getUserScd() + " - " + loggedInUserContext.getUserName();
            lowerCaseMap.put("euser", userName);
            lowerCaseMap.put("__action", action);

            String jsonPayload = objectMapper.writeValueAsString(lowerCaseMap);
            jdbcTemplate.update(sql, orgCode, programId, userName, tableName, jsonPayload,action);
            System.out.println("Successfully called auth procedure for: " + programId);
            System.out.println("Payload: " + jsonPayload);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            System.err.println("Error serializing payload for auth procedure: " + e.getMessage());
            throw new RuntimeException("Serialization failed", e);
        } catch (Exception e) {
            System.err.println("Error calling auth procedure: " + e.getMessage());
            throw new RuntimeException("Authorization procedure execution failed", e);
        }
    }
}
