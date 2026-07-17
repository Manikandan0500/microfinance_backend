package com.bbots.mfin.dto;
 
public class ExchangeResponse {
    private String child_token;
    private String roleType;
    private SessionDataDto session_data;
 
    public ExchangeResponse() {}
 
    public ExchangeResponse(String child_token, String roleType, SessionDataDto session_data) {
        this.child_token = child_token;
        this.roleType = roleType;
        this.session_data = session_data;
    }
 
    public String getChild_token() { return child_token; }
    public void setChild_token(String child_token) { this.child_token = child_token; }
 
    public String getRoleType() { return roleType; }
    public void setRoleType(String roleType) { this.roleType = roleType; }
 
    public SessionDataDto getSession_data() { return session_data; }
    public void setSession_data(SessionDataDto session_data) { this.session_data = session_data; }
}
 