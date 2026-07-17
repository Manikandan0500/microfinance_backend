package com.bbots.mfin.dto;

public class GenerateOtpResponse {
    private String tokenKey;
    private String message;

    public GenerateOtpResponse(String tokenKey, String message) {
        this.tokenKey = tokenKey;
        this.message = message;
    }

    public String getTokenKey() { return tokenKey; }
    public void setTokenKey(String tokenKey) { this.tokenKey = tokenKey; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}