package com.bbots.mfin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VerifyDto {

    private Long orgcode;
    private Long prodcode;
    private LocalDate gendate;
    private Long verifycode;
    private String pgmid;
    private String reqby;
    private LocalDateTime gentime;
    private String otphash;
    private LocalDateTime expiretime;
    private String transportmode;
    private String tokenkey;
    private Integer vresult;
    private Integer retrycnt;
    private LocalDateTime blockeduntil;

    public Long getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(Long orgcode) {
        this.orgcode = orgcode;
    }

    public Long getProdcode() {
        return prodcode;
    }

    public void setProdcode(Long prodcode) {
        this.prodcode = prodcode;
    }

    public LocalDate getGendate() {
        return gendate;
    }

    public void setGendate(LocalDate gendate) {
        this.gendate = gendate;
    }

    public Long getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(Long verifycode) {
        this.verifycode = verifycode;
    }

    public String getPgmid() {
        return pgmid;
    }

    public void setPgmid(String pgmid) {
        this.pgmid = pgmid;
    }

    public String getReqby() {
        return reqby;
    }

    public void setReqby(String reqby) {
        this.reqby = reqby;
    }

    public LocalDateTime getGentime() {
        return gentime;
    }

    public void setGentime(LocalDateTime gentime) {
        this.gentime = gentime;
    }

    public String getOtphash() {
        return otphash;
    }

    public void setOtphash(String otphash) {
        this.otphash = otphash;
    }

    public LocalDateTime getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(LocalDateTime expiretime) {
        this.expiretime = expiretime;
    }

    public String getTransportmode() {
        return transportmode;
    }

    public void setTransportmode(String transportmode) {
        this.transportmode = transportmode;
    }

    public String getTokenkey() {
        return tokenkey;
    }

    public void setTokenkey(String tokenkey) {
        this.tokenkey = tokenkey;
    }

    public Integer getVresult() {
        return vresult;
    }

    public void setVresult(Integer vresult) {
        this.vresult = vresult;
    }

    public Integer getRetrycnt() {
        return retrycnt;
    }

    public void setRetrycnt(Integer retrycnt) {
        this.retrycnt = retrycnt;
    }
    
    public LocalDateTime getBlockeduntil() { 
    	return blockeduntil; 
    }
    public void setBlockeduntil(LocalDateTime blockeduntil) { 
    	this.blockeduntil = blockeduntil; 
    }
}