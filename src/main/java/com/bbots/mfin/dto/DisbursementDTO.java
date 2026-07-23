package com.bbots.mfin.dto;

import java.util.List;


public class DisbursementDTO {

    private Long orgcode;

    private String loan_account_no;

    private Integer disbursement_seq_no;

    private Double disbursement_amount;

    private String currency_code;

    private String disbursement_mode;

    private String bank_ref_no;

    private String disbursed_by_user_id;

    private String disbursement_date;

    private String disbursement_status;

    private String acc_posting_ref;

    private String acc_posting_status;

    private String euser;

    private String edate;

    private String auser;

    private String adate;

    private String cuser;

    private String cdate;

    private Double principal_outstanding;

    private Double interest_outstanding;

    private Double total_outstanding;
    
    private String queue_id;

    public String getQueue_id() {
		return queue_id;
	}

	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}

	private List<RepaymentScheduleDTO> repaymentSchedule;

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

	public Integer getDisbursement_seq_no() {
		return disbursement_seq_no;
	}

	public void setDisbursement_seq_no(Integer disbursement_seq_no) {
		this.disbursement_seq_no = disbursement_seq_no;
	}

	public Double getDisbursement_amount() {
		return disbursement_amount;
	}

	public void setDisbursement_amount(Double disbursement_amount) {
		this.disbursement_amount = disbursement_amount;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getDisbursement_mode() {
		return disbursement_mode;
	}

	public void setDisbursement_mode(String disbursement_mode) {
		this.disbursement_mode = disbursement_mode;
	}

	public String getBank_ref_no() {
		return bank_ref_no;
	}

	public void setBank_ref_no(String bank_ref_no) {
		this.bank_ref_no = bank_ref_no;
	}

	public String getDisbursed_by_user_id() {
		return disbursed_by_user_id;
	}

	public void setDisbursed_by_user_id(String disbursed_by_user_id) {
		this.disbursed_by_user_id = disbursed_by_user_id;
	}

	public String getDisbursement_date() {
		return disbursement_date;
	}

	public void setDisbursement_date(String disbursement_date) {
		this.disbursement_date = disbursement_date;
	}

	public String getDisbursement_status() {
		return disbursement_status;
	}

	public void setDisbursement_status(String disbursement_status) {
		this.disbursement_status = disbursement_status;
	}

	public String getAcc_posting_ref() {
		return acc_posting_ref;
	}

	public void setAcc_posting_ref(String acc_posting_ref) {
		this.acc_posting_ref = acc_posting_ref;
	}

	public String getAcc_posting_status() {
		return acc_posting_status;
	}

	public void setAcc_posting_status(String acc_posting_status) {
		this.acc_posting_status = acc_posting_status;
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

	public Double getPrincipal_outstanding() {
		return principal_outstanding;
	}

	public void setPrincipal_outstanding(Double principal_outstanding) {
		this.principal_outstanding = principal_outstanding;
	}

	public Double getInterest_outstanding() {
		return interest_outstanding;
	}

	public void setInterest_outstanding(Double interest_outstanding) {
		this.interest_outstanding = interest_outstanding;
	}

	public Double getTotal_outstanding() {
		return total_outstanding;
	}

	public void setTotal_outstanding(Double total_outstanding) {
		this.total_outstanding = total_outstanding;
	}

	public List<RepaymentScheduleDTO> getRepaymentSchedule() {
		return repaymentSchedule;
	}

	public void setRepaymentSchedule(List<RepaymentScheduleDTO> repaymentSchedule) {
		this.repaymentSchedule = repaymentSchedule;
	}
    
    

}