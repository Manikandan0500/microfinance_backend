package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMaster {
    @JsonProperty("id")
    private GroupMasterId id;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("branch_code")
    private Long branchCode;
    @JsonProperty("region_code")
    private String regionCode;
    @JsonProperty("regional_officer_id")
    private String regionalOfficerId;
    @JsonProperty("source_system")
    private String sourceSystem;
    @JsonProperty("source_ref_no")
    private String sourceRefNo;
    @JsonProperty("meeting_day")
    private String meetingDay;
    @JsonProperty("meeting_frequency")
    private String meetingFrequency;
    @JsonProperty("group_status")
    private String groupStatus;
    
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
