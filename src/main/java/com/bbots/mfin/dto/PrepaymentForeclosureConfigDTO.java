package com.bbots.mfin.dto;

public class PrepaymentForeclosureConfigDTO {

	private Long orgcode;
	private String product_code;
	private int lock_in_period_months;
	private String prepayment_penalty_type;
	private double prepayment_penalty_value;
	private String foreclosure_fee_type;
	private double foreclosure_fee_value;
	private String schedule_recalc_method;
	private String config_status;
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
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public int getLock_in_period_months() {
		return lock_in_period_months;
	}
	public void setLock_in_period_months(int lock_in_period_months) {
		this.lock_in_period_months = lock_in_period_months;
	}
	public String getPrepayment_penalty_type() {
		return prepayment_penalty_type;
	}
	public void setPrepayment_penalty_type(String prepayment_penalty_type) {
		this.prepayment_penalty_type = prepayment_penalty_type;
	}
	public double getPrepayment_penalty_value() {
		return prepayment_penalty_value;
	}
	public void setPrepayment_penalty_value(double prepayment_penalty_value) {
		this.prepayment_penalty_value = prepayment_penalty_value;
	}
	public String getForeclosure_fee_type() {
		return foreclosure_fee_type;
	}
	public void setForeclosure_fee_type(String foreclosure_fee_type) {
		this.foreclosure_fee_type = foreclosure_fee_type;
	}
	public double getForeclosure_fee_value() {
		return foreclosure_fee_value;
	}
	public void setForeclosure_fee_value(double foreclosure_fee_value) {
		this.foreclosure_fee_value = foreclosure_fee_value;
	}
	public String getSchedule_recalc_method() {
		return schedule_recalc_method;
	}
	public void setSchedule_recalc_method(String schedule_recalc_method) {
		this.schedule_recalc_method = schedule_recalc_method;
	}
	public String getConfig_status() {
		return config_status;
	}
	public void setConfig_status(String config_status) {
		this.config_status = config_status;
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
