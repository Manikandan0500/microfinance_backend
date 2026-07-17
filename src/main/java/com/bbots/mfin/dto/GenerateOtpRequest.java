package com.bbots.mfin.dto;

public class GenerateOtpRequest {
    private String email;
    private String firstName;
    private String userScd;
    private Long orgCode;
    private Integer productCode;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getUserScd() { return userScd; }
    public void setUserScd(String userScd) { this.userScd = userScd; }

    public Long getOrgCode() { return orgCode; }
    public void setOrgCode(Long orgCode) { this.orgCode = orgCode; }

    public Integer getProductCode() { return productCode; }
    public void setProductCode(Integer productCode) { this.productCode = productCode; }
}