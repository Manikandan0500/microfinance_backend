package com.bbots.mfin.dto;

import java.time.LocalDate;

public class Region {
	
	private RegionId id;
    
    private Long orgcode;
    private String region_code;
    private String region_name;
    private String state;
    private String zone;
    
    private String eUser;
    private String eDate;
    private String aUser;
    private String aDate;
    private String cUser;
    private String cDate;
	public Long getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(Long orgcode) {
		this.orgcode = orgcode;
	}
	public String getRegion_code() {
		return region_code;
	}
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String geteUser() {
		return eUser;
	}
	public void seteUser(String eUser) {
		this.eUser = eUser;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getaUser() {
		return aUser;
	}
	public void setaUser(String aUser) {
		this.aUser = aUser;
	}
	public String getaDate() {
		return aDate;
	}
	public void setaDate(String aDate) {
		this.aDate = aDate;
	}
	public String getcUser() {
		return cUser;
	}
	public void setcUser(String cUser) {
		this.cUser = cUser;
	}
	public String getcDate() {
		return cDate;
	}
	public void setcDate(String cDate) {
		this.cDate = cDate;
	}
	public RegionId getId() {
		return id;
	}
	public void setId(RegionId id) {
		this.id = id;
	}
	
	

}
