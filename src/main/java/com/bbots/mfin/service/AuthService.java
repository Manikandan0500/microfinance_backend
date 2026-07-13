package com.bbots.mfin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbots.mfin.dto.Auth101Config;
import com.bbots.mfin.dto.AuthConfigDTO;
import com.bbots.mfin.dto.AuthRecord;
import com.bbots.mfin.repository.AuthRepository;
import com.bbots.mfin.repository.ModuleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Auth101Config> getAllConfigs() {
        return repository.getAllConfigs();
    }

    public Auth101Config updateConfig(Auth101Config cfg) {
        repository.updateConfig(cfg);
        return repository.getConfigById(cfg.getId());
    }

    @Transactional(readOnly = true)
    public List<AuthConfigDTO> getAllAuthConfigs() {
        return repository.getAllAuthConfigs();
    }

    public void createAuthConfig(AuthConfigDTO dto) {
        repository.createAuthConfig(dto);
    }

    @Transactional(readOnly = true)
    public List<AuthRecord> getAuthQueue() {
        return repository.getQueue();
    }

    @Transactional(readOnly = true)
    public List<AuthRecord> getMyRequests(String userId) {
        return repository.getQueueByUser(userId);
    }

    public void approve(Long authSl, int level, String userId) {
        String programId = repository.getProgramId(authSl);
        Long orgCode = jdbcTemplate.queryForObject("SELECT ORGCODE FROM AUTH001 WHERE AUTHSL = ?", Long.class, authSl); 

        System.out.println("Processing Approval for Program: [" + programId + "] AuthSl: " + authSl + " OrgCode: " + orgCode);

        if ("GLJRN".equals(programId != null ? programId.trim() : "")) {
            try {
                // 1. The custom procedure does ALL the work (TRAN and GL tables)
                System.out.println("Triggering DB-level Journal Posting for AuthSl: " + authSl);
                jdbcTemplate.update("CALL pr_post_journal(?, ?)", authSl, orgCode);
                
                // 2. Clear the Authorization Queue (Delete from AUTH001 and AUTH002)
                // Now handled directly inside the PostgreSQL procedure to avoid Java query timeouts
                // jdbcTemplate.update("DELETE FROM AUTH002 WHERE authsl = ?", authSl);
                // jdbcTemplate.update("DELETE FROM AUTH001 WHERE authsl = ?", authSl);
                System.out.println("Journal Approved and Record Removed from Queue.");
                
            } catch (Exception e) {
                System.err.println("Database Journal Posting Failed: " + e.getMessage());
                throw new RuntimeException("Posting Error: " + e.getMessage(), e);
            }
        } else {
            // 3. Standard Approval Process for other programs
            repository.processAuth(authSl, level, userId, 1, "Approved");
        }
    }

    public void reject(Long authSl, int level, String userId) {
        repository.processAuth(authSl, level, userId, 0, null);
    }

    public void lockRecord(Long authSl) {
        repository.lockRecord(authSl);
    }

    public void updateAuthConfig(AuthConfigDTO dto) {
        repository.updateAuthConfig(dto);
    }

    public void deleteAuthConfig(String programId) {
        repository.deleteAuthConfig(programId);
    }

    public void correction(Long authSl, int level, String userId, String remarks) {
        repository.processAuth(authSl, level, userId, 2, remarks);
    }
}
