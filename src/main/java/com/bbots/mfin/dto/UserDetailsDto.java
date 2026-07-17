package com.bbots.mfin.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDetailsDto {
    private Long orgCode;
    private String userScd;
    private int menuType;
    private String gender;
    private String dob;
    private String title;
    private String fName;
    private String mName;
    private String lName;
    private String email;
    private String mobile;
    private String callcode;
    private String country;
    private List<UserMappedProducts> products;
    private String picture;
    private String euser;
    private LocalDateTime edate;
    private String auser;
    private LocalDateTime adate;
    private String cuser;
    private LocalDateTime cdate;
    private Long brncd;
    private LocalDateTime brnModDate;
    private Integer status;

    public Long getBrncd() {
        return brncd;
    }

    public void setBrncd(Long brncd) {
        this.brncd = brncd;
    }

    public LocalDateTime getBrnModDate() {
        return brnModDate;
    }

    public void setBrnModDate(LocalDateTime brnModDate) {
        this.brnModDate = brnModDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCallcode() {
        return callcode;
    }

    public void setCallcode(String callcode) {
        this.callcode = callcode;
    }

    public List<UserMappedProducts> getProducts() {
        return products;
    }

    public void setProducts(List<UserMappedProducts> products) {
        this.products = products;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
