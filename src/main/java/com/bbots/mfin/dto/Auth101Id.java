package com.bbots.mfin.dto;
 
import java.util.Objects;
 
public class Auth101Id {
    private Long orgCode;
    private String programId;
 
    public Auth101Id() {}
 
    public Auth101Id(Long orgCode, String programId) {
        this.orgCode = orgCode;
        this.programId = programId;
    }
 
    public Long getOrgCode() {
        return orgCode;
    }
 
    public void setOrgCode(Long orgCode) {
        this.orgCode = orgCode;
    }
 
    public String getProgramId() {
        return programId;
    }
 
    public void setProgramId(String programId) {
        this.programId = programId;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auth101Id auth101Id = (Auth101Id) o;
        return Objects.equals(orgCode, auth101Id.orgCode) &&
                Objects.equals(programId, auth101Id.programId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(orgCode, programId);
    }
}