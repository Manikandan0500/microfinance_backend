package com.bbots.mfin.dto;

import java.time.LocalDate;

public class AuthQ001 {
	
    private Long orgcode;
    private String programid;
    private LocalDate effdate;
    private String eUser;
    private Long authsl;
    private String display_remarks;  
    private String datablock;

    
	public Long getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(Long orgcode) {
		this.orgcode = orgcode;
	}
	public String getProgramid() {
		return programid;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
	public LocalDate getEffdate() {
		return effdate;
	}
	public void setEffdate(LocalDate effdate) {
		this.effdate = effdate;
	}
	public String geteUser() {
		return eUser;
	}
	public void seteUser(String eUser) {
		this.eUser = eUser;
	}
	public Long getAuthsl() {
		return authsl;
	}
	public void setAuthsl(Long authsl) {
		this.authsl = authsl;
	}
	public String getDisplay_remarks() {
		return display_remarks;
	}
	public void setDisplay_remarks(String display_remarks) {
		this.display_remarks = display_remarks;
	}
	public String getDatablock() {
		return datablock;
	}
	public void setDatablock(String datablock) {
		this.datablock = datablock;
	}
    		
    		
    		
	

}
