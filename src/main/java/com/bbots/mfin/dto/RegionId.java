package com.bbots.mfin.dto;

import java.io.Serializable;
import java.util.Objects;

public class RegionId implements Serializable {

    private Long orgCode;
    private String regionCode;

    public RegionId() {}

    public RegionId(Long orgCode, String regionCode) {
        this.orgCode = orgCode;
        this.regionCode = regionCode;
    }

    public Long getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(Long orgCode) {
        this.orgCode = orgCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionId regionId = (RegionId) o;
        return Objects.equals(orgCode, regionId.orgCode) &&
               Objects.equals(regionCode, regionId.regionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgCode, regionCode);
    }
}
