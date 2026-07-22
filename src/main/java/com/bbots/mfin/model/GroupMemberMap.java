package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberMap {
    @JsonProperty("id")
    private GroupMemberMapId id;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("member_role")
    private String memberRole;
    @JsonProperty("join_date")
    private LocalDateTime joinDate;
    @JsonProperty("member_status")
    private String memberStatus;
    
    @JsonProperty("euser")
    private String eUser;
    @JsonProperty("edate")
    private LocalDateTime eDate;
    @JsonProperty("auser")
    private String aUser;
    @JsonProperty("adate")
    private LocalDateTime aDate;
    @JsonProperty("cuser")
    private String cUser;
    @JsonProperty("cdate")
    private LocalDateTime cDate;
}
