package com.bbots.mfin.dto;

public class DisbursementQueueDTO {

	private Long orgcode;

	private String queue_id;

	private String source_system;

	private String source_ref_no;

	private String client_id;

	private String group_code;

	private String product_code;

	private Double approved_amount;

	private Integer approved_tenure_months;

	private Double approved_interest_rate;

	private String queue_date;

	private String assigned_to_user_id;

	private String disbursement_status;

	private String currency_code;

	public Long getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(Long orgcode) {
		this.orgcode = orgcode;
	}

	public String getQueue_id() {
		return queue_id;
	}

	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getSource_ref_no() {
		return source_ref_no;
	}

	public void setSource_ref_no(String source_ref_no) {
		this.source_ref_no = source_ref_no;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public Double getApproved_amount() {
		return approved_amount;
	}

	public void setApproved_amount(Double approved_amount) {
		this.approved_amount = approved_amount;
	}

	public Integer getApproved_tenure_months() {
		return approved_tenure_months;
	}

	public void setApproved_tenure_months(Integer approved_tenure_months) {
		this.approved_tenure_months = approved_tenure_months;
	}

	public Double getApproved_interest_rate() {
		return approved_interest_rate;
	}

	public void setApproved_interest_rate(Double approved_interest_rate) {
		this.approved_interest_rate = approved_interest_rate;
	}

	public String getQueue_date() {
		return queue_date;
	}

	public void setQueue_date(String queue_date) {
		this.queue_date = queue_date;
	}

	public String getAssigned_to_user_id() {
		return assigned_to_user_id;
	}

	public void setAssigned_to_user_id(String assigned_to_user_id) {
		this.assigned_to_user_id = assigned_to_user_id;
	}

	public String getDisbursement_status() {
		return disbursement_status;
	}

	public void setDisbursement_status(String disbursement_status) {
		this.disbursement_status = disbursement_status;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	
	
}
