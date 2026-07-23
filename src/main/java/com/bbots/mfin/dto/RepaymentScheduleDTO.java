package com.bbots.mfin.dto;

public class RepaymentScheduleDTO {

    private Integer installment_no;

    private String due_date;

    private Double principal_due;

    private Double interest_due;

    private Double total_due;

    private Double principal_paid;

    private Double interest_paid;

    private String installment_status;

	public Integer getInstallment_no() {
		return installment_no;
	}

	public void setInstallment_no(Integer installment_no) {
		this.installment_no = installment_no;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public Double getPrincipal_due() {
		return principal_due;
	}

	public void setPrincipal_due(Double principal_due) {
		this.principal_due = principal_due;
	}

	public Double getInterest_due() {
		return interest_due;
	}

	public void setInterest_due(Double interest_due) {
		this.interest_due = interest_due;
	}

	public Double getTotal_due() {
		return total_due;
	}

	public void setTotal_due(Double total_due) {
		this.total_due = total_due;
	}

	public Double getPrincipal_paid() {
		return principal_paid;
	}

	public void setPrincipal_paid(Double principal_paid) {
		this.principal_paid = principal_paid;
	}

	public Double getInterest_paid() {
		return interest_paid;
	}

	public void setInterest_paid(Double interest_paid) {
		this.interest_paid = interest_paid;
	}

	public String getInstallment_status() {
		return installment_status;
	}

	public void setInstallment_status(String installment_status) {
		this.installment_status = installment_status;
	}
    
    

}