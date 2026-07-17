package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    @com.fasterxml.jackson.annotation.JsonProperty("orgCode")
    private Long orgcode;

    @com.fasterxml.jackson.annotation.JsonProperty("brnCd")
    private Long brncd;

    @com.fasterxml.jackson.annotation.JsonProperty("brnName")
    private String brnname;

    @com.fasterxml.jackson.annotation.JsonProperty("openDate")
    private Date opendate;

    private String address;

    private String country;

    @com.fasterxml.jackson.annotation.JsonProperty("divisionName")
    private String divisionname;

    private String pincode;

    private String addrline1;
    private String addrline2;
    private String addrline3;
    private String addrline4;
    private String addrline5;

    private String telephone;

    private String email;

    private Integer status;

    @com.fasterxml.jackson.annotation.JsonProperty("eUser")
    private String euser;

    @com.fasterxml.jackson.annotation.JsonProperty("eDate")
    private Date edate;

    @com.fasterxml.jackson.annotation.JsonProperty("aUser")
    private String auser;

    @com.fasterxml.jackson.annotation.JsonProperty("aDate")
    private Date adate;

    @com.fasterxml.jackson.annotation.JsonProperty("cUser")
    private String cuser;

    @com.fasterxml.jackson.annotation.JsonProperty("cDate")
    private Date cdate;

    @com.fasterxml.jackson.annotation.JsonProperty("headBrn")
    private Integer headbrn;
}
