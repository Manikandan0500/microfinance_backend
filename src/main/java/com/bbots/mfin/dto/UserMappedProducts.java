package com.bbots.mfin.dto;

public class UserMappedProducts {
	private Long orgcode;
	private Long prodcode;
	private String prodname;
	private String homeUrl;
	private Boolean status;
	private String euser;
	private java.time.LocalDateTime edate;
	private String auser;
	private java.time.LocalDateTime adate;
	private String cuser;
	private java.time.LocalDateTime cdate;
	private String logo;
	private String userscd;
	private Long accesscd;

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

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getEuser() {
		return euser;
	}

	public void setEuser(String euser) {
		this.euser = euser;
	}

	public java.time.LocalDateTime getEdate() {
		return edate;
	}

	public void setEdate(java.time.LocalDateTime edate) {
		this.edate = edate;
	}

	public String getAuser() {
		return auser;
	}

	public void setAuser(String auser) {
		this.auser = auser;
	}

	public java.time.LocalDateTime getAdate() {
		return adate;
	}

	public void setAdate(java.time.LocalDateTime adate) {
		this.adate = adate;
	}

	public String getCuser() {
		return cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public java.time.LocalDateTime getCdate() {
		return cdate;
	}

	public void setCdate(java.time.LocalDateTime cdate) {
		this.cdate = cdate;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUserscd() {
		return userscd;
	}

	public void setUserscd(String userscd) {
		this.userscd = userscd;
	}

	public Long getAccesscd() {
		return accesscd;
	}

	public void setAccesscd(Long accesscd) {
		this.accesscd = accesscd;
	}

}
