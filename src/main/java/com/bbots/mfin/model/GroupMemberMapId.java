package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberMapId implements Serializable {
    @JsonProperty("orgcode")
    private Long orgcode;
    @JsonProperty("group_code")
    private String groupCode;
    @JsonProperty("client_id")
    private String clientId;
}
