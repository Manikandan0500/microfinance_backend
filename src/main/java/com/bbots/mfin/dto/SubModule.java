package com.bbots.mfin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class SubModule {
    @com.fasterxml.jackson.annotation.JsonProperty("orgcode")
    @com.fasterxml.jackson.annotation.JsonAlias({"orgCode", "orgcode", "org_code"})
    private Long orgcode;

    @com.fasterxml.jackson.annotation.JsonProperty("module_id")
    @com.fasterxml.jackson.annotation.JsonAlias({"moduleId", "moduleid"})
    private Integer moduleId;

    @com.fasterxml.jackson.annotation.JsonProperty("sub_moduleid")
    @com.fasterxml.jackson.annotation.JsonAlias({"subModuleId", "submoduleid", "sub_module_id"})
    private Integer subModuleId;

    @com.fasterxml.jackson.annotation.JsonProperty("sub_modulename")
    @com.fasterxml.jackson.annotation.JsonAlias({"subModuleName", "submodulename", "sub_modulename", "sub_module_name"})
    private String subModuleName;
    private Integer status;
    private Date eDate;
    private String eUser;
    private Date cDate;
    private String cUser;
    private Date aDate;
    private String aUser;
}
