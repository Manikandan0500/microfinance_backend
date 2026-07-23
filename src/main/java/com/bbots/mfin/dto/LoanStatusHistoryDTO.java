package com.bbots.mfin.dto;

public class LoanStatusHistoryDTO {

	    private Long orgcode;
	    private String loan_account_no;
	    private int status_seq_no;
	    private String status_from;
	    private String status_to;
	    private String changed_date;
	    private String changed_by;
	    private String remarks;

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
		public int getStatus_seq_no() {
			return status_seq_no;
		}
		public void setStatus_seq_no(int status_seq_no) {
			this.status_seq_no = status_seq_no;
		}
		public String getStatus_from() {
			return status_from;
		}
		public void setStatus_from(String status_from) {
			this.status_from = status_from;
		}
		public String getStatus_to() {
			return status_to;
		}
		public void setStatus_to(String status_to) {
			this.status_to = status_to;
		}
		public String getChanged_date() {
			return changed_date;
		}
		public void setChanged_date(String changed_date) {
			this.changed_date = changed_date;
		}
		public String getChanged_by() {
			return changed_by;
		}
		public void setChanged_by(String changed_by) {
			this.changed_by = changed_by;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
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
