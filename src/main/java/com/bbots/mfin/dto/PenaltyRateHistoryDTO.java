package com.bbots.mfin.dto;

public class PenaltyRateHistoryDTO {

    private Long orgcode;
    private String product_code;
    private String delinquency_code;
    private String eff_date;
    private String penalty_type;
    private double penalty_value;
    private String rate_status;

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

    public String getDelinquency_code() {
        return delinquency_code;
    }

    public void setDelinquency_code(String delinquency_code) {
        this.delinquency_code = delinquency_code;
    }

    public String getEff_date() {
        return eff_date;
    }

    public void setEff_date(String eff_date) {
        this.eff_date = eff_date;
    }

    public String getPenalty_type() {
        return penalty_type;
    }

    public void setPenalty_type(String penalty_type) {
        this.penalty_type = penalty_type;
    }

    public double getPenalty_value() {
        return penalty_value;
    }

    public void setPenalty_value(double penalty_value) {
        this.penalty_value = penalty_value;
    }

    public String getRate_status() {
        return rate_status;
    }

    public void setRate_status(String rate_status) {
        this.rate_status = rate_status;
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