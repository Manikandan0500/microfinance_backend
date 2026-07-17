package com.bbots.mfin.dto;

public class SessionDataDto {
	 
    private String userScd;
    private String userName;
    private String email;
    private Long orgCode;
    private Boolean isOnline;
    private String lastSeen;
 
    public SessionDataDto() {
    }
 
    public SessionDataDto(String userScd, String userName, String email,
            Long orgCode, Boolean isOnline, String lastSeen) {
        this.userScd = userScd;
        this.userName = userName;
        this.email = email;
        this.orgCode = orgCode;
        this.isOnline = isOnline;
        this.lastSeen = lastSeen;
    }
 
    // Getters & Setters
    public String getUserScd() {
        return userScd;
    }
 
    public void setUserScd(String userScd) {
        this.userScd = userScd;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Long getOrgCode() {
        return orgCode;
    }
 
    public void setOrgCode(Long orgCode) {
        this.orgCode = orgCode;
    }
 
    public Boolean getIsOnline() {
        return isOnline;
    }
 
    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }
 
    public String getLastSeen() {
        return lastSeen;
    }
 
    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }
}