package com.bbots.mfin.dto;

import java.time.LocalDateTime;

public class SmtpMailConfigDto {

    private Long orgcode;
    private String provider;
    private String hostname;
    private Long smtpport;
    private String encryption;
    private String username;
    private String passhash;
    private String fromid;
    private String displayname;
    private String replytoid;
    private String euser;
    private LocalDateTime edate;
    private String auser;
    private LocalDateTime adate;
    private String cuser;
    private LocalDateTime cdate;

    public Long getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(Long orgcode) {
        this.orgcode = orgcode;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getSmtpport() {
        return smtpport;
    }

    public void setSmtpport(Long smtpport) {
        this.smtpport = smtpport;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getReplytoid() {
        return replytoid;
    }

    public void setReplytoid(String replytoid) {
        this.replytoid = replytoid;
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
