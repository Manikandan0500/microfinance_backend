package com.bbots.mfin.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class CustomUserDetails extends User {

    private final Long userScd;
    private final Long orgCode;
    private final String roleType;
    private final String userScdStr;

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             Long userScd, Long orgCode) {
        super(username, password, authorities);
        this.userScd = userScd;
        this.orgCode = orgCode;
        this.roleType = null;
        this.userScdStr = userScd != null ? userScd.toString() : null;
    }

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             Long userScd, Long orgCode,
                             String roleType, String userScdStr) {
        super(username, password, authorities);
        this.userScd = userScd;
        this.orgCode = orgCode;
        this.roleType = roleType;
        this.userScdStr = userScdStr;
    }

    public Long getUserScd() {
        return userScd;
    }

    public Long getOrgCode() {
        return orgCode;
    }

    public String getRoleType() {
        return roleType;
    }

    /** Returns userScd as a String (used by ProductController for filtering). */
    public String getUserScdStr() {
        return userScdStr;
    }
}

