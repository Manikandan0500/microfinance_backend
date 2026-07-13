package com.bbots.mfin.dto;

import java.time.LocalDate;

public class Region {

    private RegionId id;
    private String regionName;
    private String state;
    private String zone;
    private String eUser;
    private LocalDate eDate;
    private String aUser;
    private LocalDate aDate;
    private String cUser;
    private LocalDate cDate;

    public Region() {}

    public Region(RegionId id, String regionName, String state, String zone, String eUser, LocalDate eDate) {
        this.id = id;
        this.regionName = regionName;
        this.state = state;
        this.zone = zone;
        this.eUser = eUser;
        this.eDate = eDate;
    }

    public RegionId getId() {
        return id;
    }

    public void setId(RegionId id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String geteUser() {
        return eUser;
    }

    public void seteUser(String eUser) {
        this.eUser = eUser;
    }

    public LocalDate geteDate() {
        return eDate;
    }

    public void seteDate(LocalDate eDate) {
        this.eDate = eDate;
    }

    public String getaUser() {
        return aUser;
    }

    public void setaUser(String aUser) {
        this.aUser = aUser;
    }

    public LocalDate getaDate() {
        return aDate;
    }

    public void setaDate(LocalDate aDate) {
        this.aDate = aDate;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser;
    }

    public LocalDate getcDate() {
        return cDate;
    }

    public void setcDate(LocalDate cDate) {
        this.cDate = cDate;
    }
}
