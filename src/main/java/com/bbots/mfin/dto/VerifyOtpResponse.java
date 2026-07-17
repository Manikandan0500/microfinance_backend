package com.bbots.mfin.dto;

public class VerifyOtpResponse {
    private String tokenKey;
    private String message;

    public VerifyOtpResponse(String tokenKey, String message) {
        this.tokenKey = tokenKey;
        this.message = message;
    }

    public String getTokenKey() { return tokenKey; }
    public void setTokenKey(String tokenKey) { this.tokenKey = tokenKey; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}