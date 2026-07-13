package com.bbots.mfin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Module {
    @com.fasterxml.jackson.annotation.JsonProperty("orgcode")
    @com.fasterxml.jackson.annotation.JsonAlias({"orgCode", "orgcode", "org_code"})
    private Long orgcode;

    @com.fasterxml.jackson.annotation.JsonProperty("module_id")
    @com.fasterxml.jackson.annotation.JsonAlias({"moduleId", "moduleid", "module_id", "modCd", "modcd"})
    private Integer moduleId;

    @com.fasterxml.jackson.annotation.JsonProperty("modulename")
    @com.fasterxml.jackson.annotation.JsonAlias({"moduleName", "modulename", "module_name", "modName", "modname"})
    private String moduleName;

    @com.fasterxml.jackson.annotation.JsonProperty("sub_module")
    @com.fasterxml.jackson.annotation.JsonAlias({"subModuleRequired", "submodulerequired", "sub_module", "subModule", "submodule"})
    private Integer subModuleRequired; // 1: Yes, 0: No

    @com.fasterxml.jackson.annotation.JsonProperty("sub_moduleid")
    @com.fasterxml.jackson.annotation.JsonAlias({"subModuleId", "submoduleid", "sub_module_id"})
    private Integer subModuleId;

    @com.fasterxml.jackson.annotation.JsonProperty("sub_modulename")
    @com.fasterxml.jackson.annotation.JsonAlias({"subModuleName", "submodulename", "sub_modulename", "sub_module_name"})
    private String subModuleName;

    @com.fasterxml.jackson.annotation.JsonProperty("subModules")
    @com.fasterxml.jackson.annotation.JsonAlias({"subModules", "sub_modules", "submodules"})
    private java.util.List<SubModule> subModules;

    private Integer status; // 1: Enable, 0: Disable
    private Date eDate;
    private String eUser;
    private Date cDate;
    private String cUser;
    private Date aDate;
    private String aUser;
}
