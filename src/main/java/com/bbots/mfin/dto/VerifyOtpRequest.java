package com.bbots.mfin.dto;

public class VerifyOtpRequest {
    private String otp;
    private String tokenKey;

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public String getTokenKey() { return tokenKey; }
    public void setTokenKey(String tokenKey) { this.tokenKey = tokenKey; }
}