package com.bbots.mfin.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbots.mfin.dto.Auth101Config;
import com.bbots.mfin.dto.AuthConfigDTO;
import com.bbots.mfin.dto.AuthDataBlock;
import com.bbots.mfin.dto.AuthRecord;
import com.bbots.mfin.dto.Module;
import com.bbots.mfin.dto.SubModule;
import com.bbots.mfin.repository.AuthRepository;
import com.bbots.mfin.repository.ModuleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.bbots.mfin.model.LoanApplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
 
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
 
    public List<AuthConfigDTO> getAllAuthConfigs() {
        return repository.getAllAuthConfigs();
    }
 
    public void createAuthConfig(AuthConfigDTO dto) {
        repository.createAuthConfig(dto);
    }
 
    public List<AuthRecord> getAuthQueue(Long orgCode) {
        return repository.getQueue(orgCode);
    }
 
    public List<AuthRecord> getMyRequests(String userId) {
        return repository.getQueueByUser(userId);
    }
 
    @Transactional
    public void approve(Long authSl, int level, String userId) {
        // 1. Get metadata and data before approval processing (since the procedure may
        // delete it from queue)
        String programId = repository.getProgramId(authSl);
        List<AuthDataBlock> blocks = repository.getDataBlocks(authSl);
 
        System.out.println("Processing Approval for Program: [" + programId + "] AuthSl: " + authSl);
 
        // 2. Standard Approval Process (Procedure Call) - Status 1
        repository.processAuth(authSl, level, userId, 1, null);
 
        // 3. Post-Approval Custom Logic for Module Creation
        if (programId != null && "MOD-CRT".equals(programId.trim())) {
            try {
                System.out.println("Executing Post-Approval Sub-Module Sync. Blocks found: "
                        + (blocks != null ? blocks.size() : 0));
                if (blocks != null) {
                    for (AuthDataBlock block : blocks) {
                        System.out.println("Processing DataBlock: " + block.getDataBlock());
                        // Parse the JSON data block
                        Module m = objectMapper.readValue(block.getDataBlock(), Module.class);
 
                        System.out.println(
                                "Parsed Module: ID=" + m.getModuleId() + ", SubModuleReq=" + m.getSubModuleRequired());
 
                        // If sub-modules list is provided (New Grid Logic)
                        if (m.getSubModules() != null && !m.getSubModules().isEmpty()) {
                            for (SubModule sm : m.getSubModules()) {
                                if (sm.getOrgcode() == null)
                                    sm.setOrgcode(m.getOrgcode());
                                if (sm.getModuleId() == null)
                                    sm.setModuleId(m.getModuleId());
                                if (sm.getStatus() == null)
                                    sm.setStatus(1);
                                if (sm.getEUser() == null)
                                    sm.setEUser(m.getEUser());
 
                                System.out.println("Saving SubModule from list: " + sm.getSubModuleId() + " - "
                                        + sm.getSubModuleName());
                                moduleRepository.saveSubModule(sm);
                            }
                            System.out.println("✅ Bulk auto-created " + m.getSubModules().size()
                                    + " Sub-Modules for Module: " + m.getModuleId());
                        }
                        // Fallback: If sub-module is required and flat fields are provided (Legacy
                        // Logic)
                        else if (m.getSubModuleRequired() != null && m.getSubModuleRequired() == 1
&& m.getSubModuleId() != null) {
                            SubModule sm = new SubModule();
                            sm.setOrgcode(m.getOrgcode());
                            sm.setModuleId(m.getModuleId());
                            sm.setSubModuleId(m.getSubModuleId());
                            sm.setSubModuleName(m.getSubModuleName());
                            sm.setStatus(1);
                            sm.setEUser(m.getEUser());
 
                            System.out.println("Saving Single Fallback SubModule: " + sm.getSubModuleId() + " - "
                                    + sm.getSubModuleName());
                            moduleRepository.saveSubModule(sm);
                            System.out.println("✅ Auto-created single Sub-Module for Module: " + m.getModuleId());
                        } else {
                            System.out.println("ℹ️ No Sub-Module details to sync for this module creation.");
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ Error in Post-Approval Sub-Module creation: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (programId != null && "LOANAPP".equals(programId.trim())) {
            try {
                System.out.println("Executing Post-Approval Loan Creation. Blocks found: "
                        + (blocks != null ? blocks.size() : 0));
                if (blocks != null) {
                    for (AuthDataBlock block : blocks) {
                        System.out.println("Processing DataBlock: " + block.getDataBlock());
                        LoanApplication app = objectMapper.readValue(block.getDataBlock(), LoanApplication.class);
                        
                        String sql1 = "INSERT INTO loandev.loan001 (orgcode, loan_account_no, queue_id, client_id, " +
                                "group_code, product_code, currency_code, disbursed_amount, disbursement_date, " +
                                "maturity_date, loan_status, outstanding_principal, outstanding_interest, " +
                                "euser, edate, auser, adate) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, 0.0, ?, ?, 'Approved', 0.0, 0.0, ?, ?, ?, ?)";
                                
                        LocalDate disbursementDate = LocalDate.now();
                        LocalDate maturityDate = app.getApprovedTenureMonths() != null ? 
                                disbursementDate.plusMonths(app.getApprovedTenureMonths()) : disbursementDate.plusMonths(12);

                        jdbcTemplate.update(sql1, app.getOrgCode(), app.getSourceRefNo(), app.getQueueId(),
                                app.getClientId(), app.getGroupCode(), app.getProductCode(), app.getCurrencyCode(),
                                java.sql.Date.valueOf(disbursementDate), java.sql.Date.valueOf(maturityDate),
                                app.getEUser(), app.getEDate() != null ? Timestamp.valueOf(app.getEDate()) : null,
                                userId, Timestamp.valueOf(LocalDateTime.now()));
                        
                        String sql2 = "INSERT INTO loandev.loan002 (orgcode, loan_account_no, status_seq_no, " +
                                "status_from, status_to, changed_date, changed_by, remarks, euser, edate, auser, adate) " +
                                "VALUES (?, ?, 1, ?, 'InProgress', ?, ?, 'Loan Approved', ?, ?, ?, ?)";
                        jdbcTemplate.update(sql2, app.getOrgCode(), app.getSourceRefNo(), app.getDisbursementStatus(),
                                Timestamp.valueOf(LocalDateTime.now()), userId,
                                userId, Timestamp.valueOf(LocalDateTime.now()), userId, Timestamp.valueOf(LocalDateTime.now()));
                        
                        String sql3 = "INSERT INTO loandev.loan003 (orgcode, loan_account_no, as_on_date, " +
                                "principal_outstanding, interest_outstanding, penalty_outstanding, total_outstanding, " +
                                "euser, edate, auser, adate) " +
                                "VALUES (?, ?, ?, 0, 0, 0, 0, ?, ?, ?, ?)";
                        jdbcTemplate.update(sql3, app.getOrgCode(), app.getSourceRefNo(), java.sql.Date.valueOf(LocalDate.now()),
                                userId, Timestamp.valueOf(LocalDateTime.now()), userId, Timestamp.valueOf(LocalDateTime.now()));
                        
                        System.out.println("✅ Auto-created LOAN001, LOAN002, LOAN003 for Loan Account: " + app.getSourceRefNo());
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ Error in Post-Approval Loan creation: " + e.getMessage());
                e.printStackTrace();
            }
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
        // Status 2 for Correction Requested
        repository.processAuth(authSl, level, userId, 2, remarks);
    }
}