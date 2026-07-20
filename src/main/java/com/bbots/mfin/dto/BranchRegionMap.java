package com.bbots.mfin.dto;

public class BranchRegionMap {
    
    private BranchRegionMapId id;
    
    private Long orgcode;
    private Long branch_code;
    private String region_code;
    
    private String eUser;
    private String eDate;
    private String aUser;
    private String aDate;
    private String cUser;
    private String cDate;

    public BranchRegionMapId getId() {
        return id;
    }

    public void setId(BranchRegionMapId id) {
        this.id = id;
    }

    public Long getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(Long orgcode) {
        this.orgcode = orgcode;
    }

    public Long getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(Long branch_code) {
        this.branch_code = branch_code;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String geteUser() {
        return eUser;
    }

    public void seteUser(String eUser) {
        this.eUser = eUser;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getaUser() {
        return aUser;
    }

    public void setaUser(String aUser) {
        this.aUser = aUser;
    }

    public String getaDate() {
        return aDate;
    }

    public void setaDate(String aDate) {
        this.aDate = aDate;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }
}
