package com.bbots.mfin.dto;

import org.springframework.stereotype.Component;

@Component
public class LoggedInUserContext {

    private String userScd;
    private String userName;

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

    public void clear() {
        this.userScd = null;
        this.userName = null;
    }
}