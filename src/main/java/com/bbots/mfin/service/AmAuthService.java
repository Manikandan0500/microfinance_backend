package com.bbots.mfin.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.AuthRequest;
import com.bbots.mfin.dto.AuthResponse;
import com.bbots.mfin.dto.ExchangeResponse;
import com.bbots.mfin.dto.GenerateOtpRequest;
import com.bbots.mfin.dto.GenerateOtpResponse;
import com.bbots.mfin.dto.LoginResponseDto;
import com.bbots.mfin.dto.OrganizationDto;
import com.bbots.mfin.dto.ResetPasswordRequest;
import com.bbots.mfin.dto.SessionDataDto;
import com.bbots.mfin.dto.UpdatePasswordRequest;
import com.bbots.mfin.dto.UserDto;
import com.bbots.mfin.dto.VerifyDto;
import com.bbots.mfin.dto.VerifyEmailResponse;
import com.bbots.mfin.dto.VerifyOtpRequest;
import com.bbots.mfin.dto.VerifyOtpResponse;
import com.bbots.mfin.exception.BadRequestException;
import com.bbots.mfin.exception.InvalidCredentialsException;
import com.bbots.mfin.exception.InvalidTokenException;
import com.bbots.mfin.exception.PasswordReuseException;
import com.bbots.mfin.exception.UserNotFoundException;
import com.bbots.mfin.repository.AmUserRepository;
import com.bbots.mfin.repository.OrganizationRepository;
import com.bbots.mfin.repository.VerifyRepository;
import com.bbots.mfin.util.JwtService;
import com.bbots.mfin.util.LoginHistoryService;
import com.bbots.mfin.util.OtpUtil;

import io.jsonwebtoken.Claims;

@Service
public class AmAuthService {

    private final AmUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginHistoryService loginHistoryService;
    private final VerifyRepository verifyRepository;
    private final EmailService emailService;

    @Autowired
    private OrganizationRepository orgService;

    public AmAuthService(AmUserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            LoginHistoryService loginHistoryService,
            VerifyRepository verifyRepository,
            EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.loginHistoryService = loginHistoryService;
        this.verifyRepository = verifyRepository;
        this.emailService = emailService;
    }

    public AuthResponse authenticate(AuthRequest request, HttpServletRequest httpRequest) {
        UserDto user = null;

        try {
            user = repository.findByEmail(request.getEmail(), request.getProductCode());
        } catch (Exception e) {
            loginHistoryService.recordLogin(
                    httpRequest, null, null,
                    request.getProductCode() != null ? request.getProductCode().longValue() : null,
                    false, "User not found");
            throw e;
        }

        if (request.getProductCode() == null) {
            loginHistoryService.recordLogin(
                    httpRequest, user.getOrgCode(), user.getUserScd(),
                    null, false, "Missing product code");
            throw new UserNotFoundException("missing product code");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            loginHistoryService.recordLogin(
                    httpRequest, user.getOrgCode(), user.getUserScd(),
                    request.getProductCode().longValue(), false, "Invalid password");
            throw new BadCredentialsException("Invalid Password");
        }

        Optional<OrganizationDto> byOrgCode = orgService.findByOrgCode(user.getOrgCode());
        if (byOrgCode.isPresent()) {
            OrganizationDto organizationDto = byOrgCode.get();
            user.setOrgName(organizationDto.getOrgName());
            user.setOrgLogo(organizationDto.getLogo());
            user.setOrgmodDate(organizationDto.getEdate());
        }

        AuthResponse authRes = new AuthResponse();
        String jwtToken = jwtService.generateMainToken(user, request.getProductCode());
        authRes.setMotherToken(jwtToken);
        authRes.setMessage("Login Success");

        LoginResponseDto loginRes = new LoginResponseDto();
        loginRes.setEmail(user.getEmail());
        loginRes.setFirstName(user.getfName());
        loginRes.setLastName(user.getlName());
        loginRes.setOrgCode(String.valueOf(user.getOrgCode()));
        loginRes.setUserScd(String.valueOf(user.getUserScd()));
        loginRes.setRoleType(user.getRoleType());
        loginRes.setAccessCd(user.getAccessCd());
        authRes.setUser(loginRes);

        return authRes;
    }

    public VerifyEmailResponse verifyEmail(String email) {
        Optional<UserDto> user = repository.verifyEmail(email);

        if (!user.isPresent()) {
            return null;
        }
        UserDto u = user.get();

        VerifyEmailResponse response = new VerifyEmailResponse();
        response.setUserScd(u.getUserScd());
        response.setOrgCode(u.getOrgCode());
        response.setEmail(u.getEmail());
        response.setFirstName(u.getfName());
        response.setMessage("Email verified successfully");

        return response;
    }

    public ExchangeResponse exchangeToken(String incomingToken, Integer product) {
        Claims claims = jwtService.extractAllClaims(incomingToken);
        String tokenType = claims.get("token-type", String.class);

        if (product == null) {
            throw new UserNotFoundException("missing product code");
        }

        if (!"mother".equals(tokenType)) {
            throw new InvalidTokenException("Invalid token type: expected mother token");
        }

        boolean isValid = jwtService.validate(incomingToken);
        if (!isValid) {
            throw new InvalidTokenException("Token is expired or invalid");
        }

        String email = jwtService.extractUsername(incomingToken);

        UserDto user;
        try {
            user = repository.findByEmail(email, product);
        } catch (UsernameNotFoundException e) {
            // User not found in DB — token is valid but user doesn't exist / is inactive
            throw new UserNotFoundException("User not found or not active: " + email);
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : e.getCause() != null ? e.getCause().getMessage() : "Unknown error";
            // Check if root cause is a not-found error
            if (msg != null && msg.contains("User not found")) {
                throw new UserNotFoundException("User not found or not mapped to product " + product + ": " + email);
            }
            throw new RuntimeException("Exchange token failed: " + msg, e);
        }

        Optional<OrganizationDto> byOrgCode = orgService.findByOrgCode(user.getOrgCode());
        if (byOrgCode.isPresent()) {
            OrganizationDto organizationDto = byOrgCode.get();
            user.setOrgName(organizationDto.getOrgName());
            user.setOrgLogo(organizationDto.getLogo());
            user.setOrgmodDate(organizationDto.getEdate());
        }

        String accessToken = jwtService.generateChildToken(user, product);

        SessionDataDto sessionData = new SessionDataDto(
                user.getUserScd(),
                user.getfName() + " " + user.getlName(),
                user.getEmail(),
                user.getOrgCode(),
                null,
                null);

        return new ExchangeResponse(
                accessToken,
                user.getRoleType(),
                sessionData);
    }

    public boolean updatePassword(String userScd, Long orgCode, UpdatePasswordRequest request) {

        if (request.getNewPassword() == null || request.getConfirmPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password mismatch");
        }

        validatePasswordStrength(request.getNewPassword());
        validatePasswordNotReused(orgCode, userScd, request.getNewPassword());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(request.getNewPassword());

        int updated = repository.upsertPasswordByUserScd(orgCode, userScd, encodedPassword);
        if (updated == 0) {
            throw new RuntimeException("User not found or update failed");
        }

        return true;
    }

    public boolean resetPassword(String userScd, Long orgCode, UpdatePasswordRequest request) {

        if (request.getOldPassword() == null) {
            throw new BadRequestException("old password must required");
        }

        String oldPasswordByUserScd = repository.getOldPasswordByUserScd(orgCode, userScd);
        if (oldPasswordByUserScd == null || !passwordEncoder.matches(request.getOldPassword(), oldPasswordByUserScd)) {
            throw new InvalidCredentialsException("Old password is not matching");
        }

        if (request.getNewPassword() == null || request.getConfirmPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password mismatch");
        }

        validatePasswordStrength(request.getNewPassword());
        validatePasswordNotReused(orgCode, userScd, request.getNewPassword());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(request.getNewPassword());

        int updated = repository.upsertPasswordByUserScd(orgCode, userScd, encodedPassword);
        if (updated == 0) {
            throw new RuntimeException("User not found or update failed");
        }

        return true;
    }

    private void validatePasswordStrength(String password) {
        // Validation can be enabled if required
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public void validatePasswordNotReused(Long orgCode, String userScd, String newPassword) {
        List<String> oldPasswords = repository.getPasswordHistory(orgCode, userScd);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (String oldHash : oldPasswords) {
            if (encoder.matches(newPassword, oldHash)) {
                throw new PasswordReuseException("You have already used this password. Please choose a new one.");
            }
        }
    }

    // ============================================================
    // GENERATE OTP
    // ============================================================

    public GenerateOtpResponse generateAndSendOtp(GenerateOtpRequest request) {

        // ── Check existing block for this email ───────────────────────────────
        Optional<VerifyDto> existing = verifyRepository.findLatestByEmail(request.getEmail());

        if (existing.isPresent()) {
            VerifyDto latest = existing.get();
            LocalDateTime now = LocalDateTime.now();

            if (latest.getBlockeduntil() != null && now.isBefore(latest.getBlockeduntil())) {
                long secondsLeft = ChronoUnit.SECONDS.between(now, latest.getBlockeduntil());

                String waitMessage;
                if (secondsLeft < 60) {
                    waitMessage = "Please try again after " + secondsLeft + " seconds.";
                } else {
                    long minutesLeft = (secondsLeft / 60) + 1;
                    waitMessage = "Please try again after " + minutesLeft + " minutes.";
                }

                throw new InvalidTokenException(
                        "You have been temporarily blocked due to multiple failed attempts. \n" + waitMessage);
            }

            // Block expired → allow new OTP (fall through)
        }

        // ── Generate new OTP ──────────────────────────────────────────────────
        String otp = OtpUtil.generateOtp();
        String otpHash = new BCryptPasswordEncoder().encode(otp);
        String tokenKey = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusMinutes(5);

        VerifyDto dto = new VerifyDto();
        dto.setOrgcode(request.getOrgCode());
        if (request.getProductCode() != null) {
            dto.setProdcode(request.getProductCode().longValue());
        }
        dto.setGendate(LocalDate.now());
        dto.setVerifycode(System.currentTimeMillis());
        dto.setPgmid("Forgot Password");
        dto.setReqby(request.getEmail());
        dto.setGentime(now);
        dto.setOtphash(otpHash);
        dto.setExpiretime(expiry);
        dto.setTransportmode("E");
        dto.setTokenkey(tokenKey);
        dto.setVresult(0);
        dto.setRetrycnt(0);

        verifyRepository.save(dto);

        emailService.sendOtpMail(request.getOrgCode(), request.getEmail(), request.getFirstName(), otp);

        return new GenerateOtpResponse(tokenKey, "OTP sent to your email");
    }

    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {

        VerifyDto record = verifyRepository
                .findByTokenKey(request.getTokenKey())
                .orElseThrow(() -> new InvalidTokenException("Invalid token"));

        LocalDateTime now = LocalDateTime.now();

        // ── 1. Currently blocked ──────────────────────────────────────────────
        if (record.getBlockeduntil() != null && now.isBefore(record.getBlockeduntil())) {
            long secondsLeft = ChronoUnit.SECONDS.between(now, record.getBlockeduntil());
            throw new InvalidTokenException(
                    "Too many attempts. Please retry after " + secondsLeft + " seconds.");
        }

        // ── 2. Block expired → invalidate token, force new OTP ───────────────
        if (record.getBlockeduntil() != null && now.isAfter(record.getBlockeduntil())) {
            verifyRepository.updateVresult(request.getTokenKey(), 2); // invalidate
            throw new InvalidTokenException(
                    "Block period expired. Please generate a new OTP."); // force regenerate
        }

        // ── 3. Already verified ───────────────────────────────────────────────
        if (record.getVresult() == 1) {
            throw new InvalidTokenException("OTP already verified");
        }

        // ── 4. Already used/invalidated ───────────────────────────────────────
        if (record.getVresult() == 2) {
            throw new InvalidTokenException("OTP already used or expired");
        }

        // ── 5. OTP expired ────────────────────────────────────────────────────
        if (now.isAfter(record.getExpiretime())) {
            verifyRepository.updateVresult(request.getTokenKey(), 2);
            throw new InvalidTokenException("OTP expired");
        }

        // ── 6. Check OTP hash ─────────────────────────────────────────────────
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(request.getOtp(), record.getOtphash())) {

            verifyRepository.incrementRetry(request.getTokenKey());
            int newRetryCount = record.getRetrycnt() + 1;

            // ── 7. Block if 3 wrong attempts ──────────────────────────────────
            if (newRetryCount >= 3) {
                verifyRepository.blockUntil(request.getTokenKey(), now.plusMinutes(5));
                throw new InvalidTokenException(
                        "Maximum attempts reached. Please retry after 5 minutes.");
            }

            int remaining = 3 - newRetryCount;
            throw new InvalidTokenException(
                    "Invalid OTP. " + remaining + " attempts remaining.");
        }

        // ── 8. OTP correct → mark as verified ────────────────────────────────
        verifyRepository.updateVresult(request.getTokenKey(), 1);

        return new VerifyOtpResponse(request.getTokenKey(), "OTP verified successfully");
    }

    public boolean resetPasswordWithToken(
            ResetPasswordRequest request) {

        VerifyDto record = verifyRepository
                .findByTokenKey(
                        request.getTokenKey())
                .orElseThrow(() -> new InvalidTokenException(
                        "Invalid token"));

        if (record.getVresult() != 1) {

            throw new InvalidTokenException(
                    "OTP not verified");
        }

        if (LocalDateTime.now()
                .isAfter(
                        record.getExpiretime()
                                .plusMinutes(10))) {

            throw new InvalidTokenException(
                    "Reset session expired");
        }

        if (request.getNewPassword() == null
                || request.getConfirmPassword() == null) {

            throw new IllegalArgumentException(
                    "Password cannot be null");
        }

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new IllegalArgumentException(
                    "Passwords do not match");
        }

        validatePasswordStrength(
                request.getNewPassword());

        validatePasswordNotReused(
                record.getOrgcode(),
                record.getReqby(),
                request.getNewPassword());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encoded = encoder.encode(
                request.getNewPassword());
        UserDto user = repository.findUser(record.getReqby());

        int updated = repository.upsertPasswordByUserScd(
                record.getOrgcode(),
                user.getUserScd(),
                encoded);

        if (updated == 0) {

            throw new RuntimeException(
                    "Password reset failed");
        } else {
            // emailService.sendPasswordUpdateMail(
            // user.getEmail(),
            // user.getfName(),
            // request.getNewPassword());
        }

        verifyRepository.updateVresult(
                request.getTokenKey(),
                2);

        return true;
    }

}
