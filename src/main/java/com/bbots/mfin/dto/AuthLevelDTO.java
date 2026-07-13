package com.bbots.mfin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLevelDTO {
    @com.fasterxml.jackson.annotation.JsonProperty("permissionType")
    private String permissiontype;
    private int level;
    @com.fasterxml.jackson.annotation.JsonProperty("roleCd")
    private String rolecd;
    @com.fasterxml.jackson.annotation.JsonProperty("userId")
    private String userid;
}
