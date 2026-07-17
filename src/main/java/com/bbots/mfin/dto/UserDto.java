package com.bbots.mfin.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDto {
    private Long orgCode;
    private String orgName;
    private String orgLogo;
    private LocalDateTime orgmodDate;
    private LocalDateTime usermodDate;
    private String userScd;
    private int menuType;
    private String gender;
    private String title;
    private String fName;
    private String mName;
    private String lName;
    private String email;
    private String mobile;
    private String callcode;
    private String country;
    private String userName;
    private Boolean isOnline;
    private LocalDateTime lastSeen;
    private String password;
    private String password_salt;
    private List<ProductDto> products;
    private String roleType;
    private int accessCd;

    private String picture;
    
    private Long brncd;
    private Integer status;
    private String dob;
    private String euser;
    private String auser;
    private LocalDateTime edate;
    private LocalDateTime adate;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    public LocalDateTime getOrgmodDate() {
        return orgmodDate;
    }

    public void setOrgmodDate(LocalDateTime orgmodDate) {
        this.orgmodDate = orgmodDate;
    }

    public LocalDateTime getUsermodDate() {
        return usermodDate;
    }

    public void setUsermodDate(LocalDateTime usermodDate) {
        this.usermodDate = usermodDate;
    }

    public void setAccessCd(int accessCd) {
        this.accessCd = accessCd;
    }

    public String getCallcode() {
        return callcode;
    }

    public void setCallcode(String callcode) {
        this.callcode = callcode;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public String getPassword_salt() {
        return password_salt;
    }

    public void setPassword_salt(String password_salt) {
        this.password_salt = password_salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Integer getAccessCd() {
        return accessCd;
    }

    public void setAccessCd(Integer accessCd) {
        this.accessCd = accessCd;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Long getBrncd() {
        return brncd;
    }

    public void setBrncd(Long brncd) {
        this.brncd = brncd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEuser() {
        return euser;
    }

    public void setEuser(String euser) {
        this.euser = euser;
    }

    public String getAuser() {
        return auser;
    }

    public void setAuser(String auser) {
        this.auser = auser;
    }

    public LocalDateTime getEdate() {
        return edate;
    }

    public void setEdate(LocalDateTime edate) {
        this.edate = edate;
    }

    public LocalDateTime getAdate() {
        return adate;
    }

    public void setAdate(LocalDateTime adate) {
        this.adate = adate;
    }

}
