package com.bbots.mfin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bbots.mfin.dto.Auth101Config;
import com.bbots.mfin.dto.AuthConfigDTO;
import com.bbots.mfin.dto.AuthRecord;
import com.bbots.mfin.dto.AuthRequest;
import com.bbots.mfin.dto.AuthResponse;
import com.bbots.mfin.model.User;
import com.bbots.mfin.service.AuthClientService;
import com.bbots.mfin.service.AuthService;
import com.bbots.mfin.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthClientService authClientService;

    @PostMapping("/login")
    public AuthResponse callLogin(@RequestBody AuthRequest request) {
        return authClientService.login(request);
    }

    @GetMapping("/configs")
    public List<Auth101Config> getConfigs() {
        return service.getAllConfigs();
    }

    @PostMapping("/configs")
    public Auth101Config updateConfig(@RequestBody Auth101Config cfg) {
        return service.updateConfig(cfg);
    }

    @GetMapping("/authctl/list")
    public List<AuthConfigDTO> getAuthConfigs() {
        return service.getAllAuthConfigs();
    }

    @PostMapping("/authctl/create")
    public void createAuthConfig(@RequestBody AuthConfigDTO dto) {
        service.createAuthConfig(dto);
    }

    @GetMapping("/queue")
    public List<AuthRecord> getQueue(@RequestParam(required = false) Long orgCode) {
        if (orgCode == null) {
            orgCode = 101L; // Default for testing
        }
        return service.getAuthQueue(orgCode);
    }

    @PutMapping("/authctl/update")
    public void updateAuthConfig(@RequestBody AuthConfigDTO dto) {
        service.updateAuthConfig(dto);
    }

    @DeleteMapping("/authctl/delete/{programId}")
    public void deleteAuthConfig(@PathVariable String programId) {
        service.deleteAuthConfig(programId);
    }

    @PostMapping("/approve/{authSl}")
    public void approve(@PathVariable Long authSl, @RequestParam int level, @RequestParam String userId) {
        service.approve(authSl, level, userId);
    }

    @PostMapping("/reject/{authSl}")
    public void reject(@PathVariable Long authSl, @RequestParam int level, @RequestParam String userId) {
        service.reject(authSl, level, userId);
    }

    @PostMapping("/correction/{authSl}")
    public void correction(
            @PathVariable Long authSl,
            @RequestParam int level,
            @RequestParam String userId,
            @RequestParam(required = false) String remarks) {
        service.correction(authSl, level, userId, remarks);
    }

    @PostMapping("/lock/{authSl}")
    public void lock(@PathVariable Long authSl) {
        System.out.println("🚀 Received lock request for authSl: " + authSl);
        service.lockRecord(authSl);
    }

    @GetMapping("/debug/proc")
    public java.util.List<String> debugProc(@RequestParam String name) {
        return jdbcTemplate.queryForList("SELECT prosrc FROM pg_proc WHERE proname = ?", String.class, name);
    }

    @GetMapping("/debug/sql")
    public java.util.List<java.util.Map<String, Object>> debugSql(@RequestParam String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/organizations")
    public Object getOrganizations(@RequestHeader("Authorization") String token) {
        return authClientService.getOrganizations(token);
    }

    @GetMapping("/login-history")
    public Object getLoginHistory(
            @RequestParam(required = false) Long orgCode,
            @RequestParam(required = false) Long prodcode,
            @RequestParam(required = false) String usercd,
            @RequestHeader("Authorization") String token) {
        return authClientService.getLoginHistory(orgCode, prodcode, usercd, token);
    }
    

    @ExceptionHandler(Exception.class)
    public org.springframework.http.ResponseEntity<String> handleExceptions(Exception e) {
        e.printStackTrace();
        return org.springframework.http.ResponseEntity.status(500).body(e.getMessage());
    }
}

@RestController
@RequestMapping("/api")
class Auth002Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/auth002")
    public void submitAuth002(@RequestBody Map<String, Object> payload) {
        try {
            // Check what program it is. Currently we only handle USR-CRT here based on
            // flutter app
            String programId = (String) payload.get("PROGRAMID");

            if ("USR-CRT".equals(programId)) {
                // Get the DATABLOCK which is the User JSON
                Object datablockObj = payload.get("DATABLOCK");
                String datablock;
                if (datablockObj instanceof String) {
                    datablock = (String) datablockObj;
                } else {
                    datablock = objectMapper.writeValueAsString(datablockObj);
                }
                User user = objectMapper.readValue(datablock, User.class);
                userService.createUserAuthRequest(user);
            } else {
                // If it's something else, we could handle it here or throw an error
                throw new UnsupportedOperationException(
                        "Program ID " + programId + " not supported on this endpoint yet.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing Auth002 submission", e);
        }
    }
}
