package com.bbots.mfin.dto;

import java.io.Serializable;
import java.util.Objects;

public class BranchRegionMapId implements Serializable {

    private Long orgcode;
    private Long branch_code;

    public BranchRegionMapId() {}

    public BranchRegionMapId(Long orgcode, Long branch_code) {
        this.orgcode = orgcode;
        this.branch_code = branch_code;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchRegionMapId that = (BranchRegionMapId) o;
        return Objects.equals(orgcode, that.orgcode) &&
               Objects.equals(branch_code, that.branch_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgcode, branch_code);
    }
}
