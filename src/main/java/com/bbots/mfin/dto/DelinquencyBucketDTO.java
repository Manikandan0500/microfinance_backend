package com.bbots.mfin.dto;

public class DelinquencyBucketDTO {

    private Long orgcode;
    private String product_code;
    private String delinquency_code;
    private String bucket_label;
    private int overdue_days_from;
    private int overdue_days_to;
    private int stage_order;
    private String is_npa_flag;
    private double provision_pct;
    private String bucket_status;

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

    public String getBucket_label() {
        return bucket_label;
    }

    public void setBucket_label(String bucket_label) {
        this.bucket_label = bucket_label;
    }

    public int getOverdue_days_from() {
        return overdue_days_from;
    }

    public void setOverdue_days_from(int overdue_days_from) {
        this.overdue_days_from = overdue_days_from;
    }

    public int getOverdue_days_to() {
        return overdue_days_to;
    }

    public void setOverdue_days_to(int overdue_days_to) {
        this.overdue_days_to = overdue_days_to;
    }

    public int getStage_order() {
        return stage_order;
    }

    public void setStage_order(int stage_order) {
        this.stage_order = stage_order;
    }

    public String getIs_npa_flag() {
        return is_npa_flag;
    }

    public void setIs_npa_flag(String is_npa_flag) {
        this.is_npa_flag = is_npa_flag;
    }

    public double getProvision_pct() {
        return provision_pct;
    }

    public void setProvision_pct(double provision_pct) {
        this.provision_pct = provision_pct;
    }

    public String getBucket_status() {
        return bucket_status;
    }

    public void setBucket_status(String bucket_status) {
        this.bucket_status = bucket_status;
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

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getAdate() {
        return adate;
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