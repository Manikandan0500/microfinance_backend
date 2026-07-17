package com.bbots.mfin.controller;
 
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bbots.mfin.dto.AuthRequest;
import com.bbots.mfin.dto.AuthResponse;
import com.bbots.mfin.dto.GenerateOtpRequest;
import com.bbots.mfin.dto.ResetPasswordRequest;
import com.bbots.mfin.dto.VerifyEmailResponse;
import com.bbots.mfin.dto.VerifyOtpRequest;
import com.bbots.mfin.service.AmAuthService;
import com.bbots.mfin.util.LoginHistoryService;
 
 
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
 
    private final AmAuthService authService;
    private final LoginHistoryService loginHistoryService;
 
    public AuthenticationController(AmAuthService authService,
            LoginHistoryService loginHistoryService) {
        this.authService = authService;
        this.loginHistoryService = loginHistoryService;
    }
 
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.authenticate(request, httpRequest));
    }
 
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        loginHistoryService.recordLogout(request);
        return ResponseEntity.ok("Logged out successfully");
    }
 
    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        VerifyEmailResponse response = authService.verifyEmail(email);
        if (response == null) {
            return ResponseEntity.badRequest().body("Email not registered");
        }
        return ResponseEntity.ok(response);
    }
 
    @PostMapping("/forgot-password/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request) {
        boolean resetPassword = authService.resetPasswordWithToken(request);
        if (resetPassword) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update password");
        }
    }
 
    @PostMapping("/logout-internal")
    public ResponseEntity<?> logoutInternal(
            @RequestBody Map<String, String> body,
            @RequestHeader(value = "X-Internal-Call", required = false) String internalCall) {
 
        if (!"true".equals(internalCall)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
 
        String usercd = body.get("usercd");
        String orgcode = body.get("orgcode");
 
        if (usercd == null || orgcode == null) {
            return ResponseEntity.badRequest().body("usercd and orgcode are required");
        }
 
        try {
            loginHistoryService.recordLogoutInternal(usercd, Long.parseLong(orgcode));
            return ResponseEntity.ok("Logout recorded");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to record logout");
        }
    }
 
    @PostMapping("/forgot-password/generate-otp")
    public ResponseEntity<?> generateOtp(@RequestBody GenerateOtpRequest request) {
        return ResponseEntity.ok(authService.generateAndSendOtp(request));
    }
    @PostMapping("/forgot-password/verify-otp")
        public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
            return ResponseEntity.ok(authService.verifyOtp(request));
        }
}