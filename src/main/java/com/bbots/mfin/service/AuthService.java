package com.bbots.mfin.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.Auth101Config;
import com.bbots.mfin.dto.AuthConfigDTO;
import com.bbots.mfin.dto.AuthDataBlock;
import com.bbots.mfin.dto.AuthRecord;
import com.bbots.mfin.dto.Module;
import com.bbots.mfin.dto.SubModule;
import com.bbots.mfin.repository.AuthRepository;
import com.bbots.mfin.repository.ModuleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
 
import java.util.List;
 
@Service
public class AuthService {
 
    @Autowired
    private AuthRepository repository;
 
    @Autowired
    private ModuleRepository moduleRepository;
 
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