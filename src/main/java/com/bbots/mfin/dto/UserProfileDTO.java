package com.bbots.mfin.dto;

import java.io.Serializable;

public class UserProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    private String role;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
