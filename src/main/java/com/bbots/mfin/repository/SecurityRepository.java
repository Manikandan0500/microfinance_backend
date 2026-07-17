package com.bbots.mfin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class SecurityRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> findUserForAuth(String email) {
        try {
            System.out.println("🔍 SecurityRepository: Querying authentication data for email: " + email);

            // Join USER002 (email) and USER003 (password)
            String sql = "SELECT u2.emailid AS username, u3.PASSWORD_HASH AS password, 'ROLE_USER' AS roles " +
                    "FROM USER002 u2 " +
                    "JOIN USER003 u3 ON u2.USERSCD = u3.USERSCD AND u2.ORGCODE = u3.ORGCODE " +
                    "WHERE u2.emailid = ?";

            Map<String, Object> result = jdbcTemplate.queryForMap(sql, email);
            System.out.println("✅ SecurityRepository: User found in database.");
            return result;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("❌ SecurityRepository: No user found for email: " + email);
            return null;
        } catch (Exception e) {
            System.err.println("🔥 SecurityRepository: Database error during authentication: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
