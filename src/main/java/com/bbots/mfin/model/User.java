package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @com.fasterxml.jackson.annotation.JsonProperty("orgCode")
    private Long orgcode;

    @com.fasterxml.jackson.annotation.JsonProperty("usersCd")
    private String userscd;

    private String title;

    @com.fasterxml.jackson.annotation.JsonProperty("fName")
    private String fname;

    @com.fasterxml.jackson.annotation.JsonProperty("mName")
    private String mname;

    @com.fasterxml.jackson.annotation.JsonProperty("lName")
    private String lname;

    @com.fasterxml.jackson.annotation.JsonProperty("regDate")
    private Date regdate;

    private Integer status;

    @com.fasterxml.jackson.annotation.JsonProperty("branchCd")
    private String branchcd;

    private String picture;

    private String gender;

    @com.fasterxml.jackson.annotation.JsonProperty("dob")
    private Date dob;

    private String emailid;

    @com.fasterxml.jackson.annotation.JsonProperty("callCode")
    private Long callcode;

    private Integer country;

    private String mobile;

    @com.fasterxml.jackson.annotation.JsonProperty("passwordHash")
    private String passwordHash;

    @com.fasterxml.jackson.annotation.JsonProperty("passwordSalt")
    private String passwordSalt;

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
}
