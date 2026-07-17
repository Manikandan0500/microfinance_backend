package com.bbots.mfin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BranchDto {

	 private Long orgCode;
	    private Long brncd;
	    private String brnname;
	    private LocalDate openDate;
	    private String address;
	    private String country;
	    private String divisionName;
	    private String pincode;

	    private String addrline1;
	    private String addrline2;
	    private String addrline3;
	    private String addrline4;
	    private String addrline5;

	    private String telephone;
	    private String email;

	    private Boolean status;
	    private Boolean headbrn;

	    private String euser;
	    private LocalDateTime edate;

	    private String auser;
	    private LocalDateTime adate;

	    private String cuser;
	    private LocalDateTime cdate;

	    // Optional field for UI/display purposes
	    private String userName;
	    private Long pgmId;
	    
	    
	    
		public BranchDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public BranchDto(Long orgCode, Long brncd, String brnname, LocalDate openDate, String address, String country,
				String divisionName, String pincode, String addrline1, String addrline2, String addrline3,
				String addrline4, String addrline5, String telephone, String email, Boolean status, Boolean headbrn,
				String euser, LocalDateTime edate, String auser, LocalDateTime adate, String cuser, LocalDateTime cdate,
				String userName, Long pgmId) {
			super();
			this.orgCode = orgCode;
			this.brncd = brncd;
			this.brnname = brnname;
			this.openDate = openDate;
			this.address = address;
			this.country = country;
			this.divisionName = divisionName;
			this.pincode = pincode;
			this.addrline1 = addrline1;
			this.addrline2 = addrline2;
			this.addrline3 = addrline3;
			this.addrline4 = addrline4;
			this.addrline5 = addrline5;
			this.telephone = telephone;
			this.email = email;
			this.status = status;
			this.headbrn = headbrn;
			this.euser = euser;
			this.edate = edate;
			this.auser = auser;
			this.adate = adate;
			this.cuser = cuser;
			this.cdate = cdate;
			this.userName = userName;
			this.pgmId = pgmId;
		}
		public Long getOrgCode() {
			return orgCode;
		}
		public void setOrgCode(Long orgCode) {
			this.orgCode = orgCode;
		}
		public Long getBrncd() {
			return brncd;
		}
		public void setBrncd(Long brncd) {
			this.brncd = brncd;
		}
		public String getBrnname() {
			return brnname;
		}
		public void setBrnname(String brnname) {
			this.brnname = brnname;
		}
		public LocalDate getOpenDate() {
			return openDate;
		}
		public void setOpenDate(LocalDate openDate) {
			this.openDate = openDate;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getDivisionName() {
			return divisionName;
		}
		public void setDivisionName(String divisionName) {
			this.divisionName = divisionName;
		}
		public String getPincode() {
			return pincode;
		}
		public void setPincode(String pincode) {
			this.pincode = pincode;
		}
		public String getAddrline1() {
			return addrline1;
		}
		public void setAddrline1(String addrline1) {
			this.addrline1 = addrline1;
		}
		public String getAddrline2() {
			return addrline2;
		}
		public void setAddrline2(String addrline2) {
			this.addrline2 = addrline2;
		}
		public String getAddrline3() {
			return addrline3;
		}
		public void setAddrline3(String addrline3) {
			this.addrline3 = addrline3;
		}
		public String getAddrline4() {
			return addrline4;
		}
		public void setAddrline4(String addrline4) {
			this.addrline4 = addrline4;
		}
		public String getAddrline5() {
			return addrline5;
		}
		public void setAddrline5(String addrline5) {
			this.addrline5 = addrline5;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Boolean getStatus() {
			return status;
		}
		public void setStatus(Boolean status) {
			this.status = status;
		}
		public Boolean getHeadbrn() {
			return headbrn;
		}
		public void setHeadbrn(Boolean headbrn) {
			this.headbrn = headbrn;
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
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Long getPgmId() {
			return pgmId;
		}
		public void setPgmId(Long pgmId) {
			this.pgmId = pgmId;
		}

	    
	    
	    
}
