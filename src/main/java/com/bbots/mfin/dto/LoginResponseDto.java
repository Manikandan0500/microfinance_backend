package com.bbots.mfin.dto;

public class LoginResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private String orgCode;
    private String userScd;
    private String roleType;
    private int accessCd;

    public LoginResponseDto() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getOrgCode() { return orgCode; }
    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

    public String getUserScd() { return userScd; }
    public void setUserScd(String userScd) { this.userScd = userScd; }

    public String getRoleType() { return roleType; }
    public void setRoleType(String roleType) { this.roleType = roleType; }

    public int getAccessCd() { return accessCd; }
    public void setAccessCd(int accessCd) { this.accessCd = accessCd; }
}
