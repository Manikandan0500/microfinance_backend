package com.bbots.mfin.dto;

public class VerifyEmailResponse {

    private String userScd;
    private String email;
    private String firstName;
    private String message;
    private Long orgCode;

    public Long getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(Long orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserScd() {
        return userScd;
    }

    public void setUserScd(String userScd) {
        this.userScd = userScd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
