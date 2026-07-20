package com.bbots.mfin.dto;

public class RateRevisionHistoryDTO {

    private Long orgcode;
    private String product_code;
    private String eff_date;
    private double revised_rate;
    private String benchmark_rate_code;
    private double spread_pct;
    private String revision_reason;
    private String revision_status;

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
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public double getRevised_rate() {
		return revised_rate;
	}
	public void setRevised_rate(double revised_rate) {
		this.revised_rate = revised_rate;
	}
	public String getBenchmark_rate_code() {
		return benchmark_rate_code;
	}
	public void setBenchmark_rate_code(String benchmark_rate_code) {
		this.benchmark_rate_code = benchmark_rate_code;
	}
	public double getSpread_pct() {
		return spread_pct;
	}
	public void setSpread_pct(double spread_pct) {
		this.spread_pct = spread_pct;
	}
	public String getRevision_reason() {
		return revision_reason;
	}
	public void setRevision_reason(String revision_reason) {
		this.revision_reason = revision_reason;
	}
	public String getRevision_status() {
		return revision_status;
	}
	public void setRevision_status(String revision_status) {
		this.revision_status = revision_status;
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