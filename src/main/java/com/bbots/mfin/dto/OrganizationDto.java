package com.bbots.mfin.dto;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
 
import com.fasterxml.jackson.annotation.JsonAlias;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class OrganizationDto {
 
    @JsonProperty("orgcode")
    private Long orgCode;
 
    // @JsonAlias({ "name" })
    // private String orgName;
 
    @JsonAlias({ "name" })
    private String name;
 
    @JsonAlias({ "opendate" })
    private LocalDate openDate;
 
    private String logo;
    private String country;
 
    @JsonAlias({ "divisionname" })
    private String divisionName;
 
    private String pincode;
 
    @JsonAlias({ "addrline1" })
    private String addressLine1;
 
    @JsonAlias({ "addrline2" })
    private String addressLine2;
 
    @JsonAlias({ "addrline3" })
    private String addressLine3;
 
    @JsonAlias({ "addrline4" })
    private String addressLine4;
 
    @JsonAlias({ "addrline5" })
    private String addressLine5;
 
    private String telephone;
    private String email;
 
    private Integer status;
    private Integer indiv;
    private String userName;
    private String euser;
    private LocalDateTime edate;
    private String auser;
    private LocalDateTime adate;
    private String cuser;
    private LocalDateTime cdate;
 
    public OrganizationDto() {
    }
 
    // public OrganizationDto(Long orgCode, String orgName, LocalDate openDate,
    // String country,
    // String divisionName, String pincode,
    // String addressLine1, String addressLine2, String addressLine3, String
    // addressLine4, String addressLine5,
    // String telephone, String email,
    // Integer status, Integer indiv, String userName) {
    // this.orgCode = orgCode;
    // this.orgName = orgName;
    // this.openDate = openDate;
    // this.country = country;
    // this.divisionName = divisionName;
    // this.pincode = pincode;
    // this.addressLine1 = addressLine1;
    // this.addressLine2 = addressLine2;
    // this.addressLine3 = addressLine3;
    // this.addressLine4 = addressLine4;
    // this.addressLine5 = addressLine5;
    // this.telephone = telephone;
    // this.email = email;
    // this.status = status;
    // this.indiv = indiv;
    // this.userName = userName;
    // }
 
    public Long getOrgCode() {
        return orgCode;
    }
 
    public void setOrgCode(Long orgCode) {
        this.orgCode = orgCode;
    }
 
    public String getOrgName() {
        return name;
    }
 
    public void setOrgName(String name) {
        this.name = name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public LocalDate getOpenDate() {
        return openDate;
    }
 
    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }
 
    public String getLogo() {
        return logo;
    }
 
    public void setLogo(String logo) {
        this.logo = logo;
    }
 
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
 
    public String getDivisionName() {
        return divisionName;
    }
 
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
 
    public String getPincode() {
        return pincode;
    }
 
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
 
    public String getAddressLine1() {
        return addressLine1;
    }
 
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
 
    public String getAddressLine2() {
        return addressLine2;
    }
 
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
 
    public String getAddressLine3() {
        return addressLine3;
    }
 
    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }
 
    public String getAddressLine4() {
        return addressLine4;
    }
 
    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }
 
    public String getAddressLine5() {
        return addressLine5;
    }
 
    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }
 
    public String getTelephone() {
        return telephone;
    }
 
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Integer getStatus() {
        return status;
    }
 
    public void setStatus(Integer status) {
        this.status = status;
    }
 
    public Integer getIndiv() {
        return indiv;
    }
 
    public void setIndiv(Integer indiv) {
        this.indiv = indiv;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getEuser() {
        return euser;
    }
 
    public void setEuser(String euser) {
        this.euser = euser;
    }
 
    public LocalDateTime getEdate() {
        return edate;
    }
 
    public void setEdate(LocalDateTime edate) {
        this.edate = edate;
    }
 
    public String getAuser() {
        return auser;
    }
 
    public void setAuser(String auser) {
        this.auser = auser;
    }
 
    public LocalDateTime getAdate() {
        return adate;
    }
 
    public void setAdate(LocalDateTime adate) {
        this.adate = adate;
    }
 
    public String getCuser() {
        return cuser;
    }
 
    public void setCuser(String cuser) {
        this.cuser = cuser;
    }
 
    public LocalDateTime getCdate() {
        return cdate;
    }
 
    public void setCdate(LocalDateTime cdate) {
        this.cdate = cdate;
    }
}