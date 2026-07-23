package com.bbots.mfin.dto;

public class LoanAccountOutstandingDTO {
	
    private Long orgcode;
    private String loan_account_no;
    private String as_on_date;
    private double principal_outstanding;
    private double interest_outstanding;
    private double penalty_outstanding;
    private double total_outstanding;

    private String euser;
    private String edate;
    private String auser;
    private String adate;
    private String cuser;
    private String cdate;
    
	public Long getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(Long orgcode) {
		this.orgcode = orgcode;
	}
	public String getLoan_account_no() {
		return loan_account_no;
	}
	public void setLoan_account_no(String loan_account_no) {
		this.loan_account_no = loan_account_no;
	}
	public String getAs_on_date() {
		return as_on_date;
	}
	public void setAs_on_date(String as_on_date) {
		this.as_on_date = as_on_date;
	}
	public double getPrincipal_outstanding() {
		return principal_outstanding;
	}
	public void setPrincipal_outstanding(double principal_outstanding) {
		this.principal_outstanding = principal_outstanding;
	}
	public double getInterest_outstanding() {
		return interest_outstanding;
	}
	public void setInterest_outstanding(double interest_outstanding) {
		this.interest_outstanding = interest_outstanding;
	}
	public double getPenalty_outstanding() {
		return penalty_outstanding;
	}
	public void setPenalty_outstanding(double penalty_outstanding) {
		this.penalty_outstanding = penalty_outstanding;
	}
	public double getTotal_outstanding() {
		return total_outstanding;
	}
	public void setTotal_outstanding(double total_outstanding) {
		this.total_outstanding = total_outstanding;
	}
	public String getEuser() {
		return euser;
	}
	public void setEuser(String euser) {
		this.euser = euser;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getAuser() {
		return auser;
	}
	public void setAuser(String auser) {
		this.auser = auser;
	}
	public String getAdate() {
		return adate;
	}
	public void setAdate(String adate) {
		this.adate = adate;
	}
	public String getCuser() {
		return cuser;
	}
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
    
    
}
