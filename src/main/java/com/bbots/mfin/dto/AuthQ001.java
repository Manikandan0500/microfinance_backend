package com.bbots.mfin.dto;

import java.time.LocalDate;

public class AuthQ001 {
	
    private Long orgcode;
    private String programid;
    private LocalDate effdate;
    private String eUser;
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
    		
    		
    		
	

}
