package com.bbots.mfin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthConfigDTO {
    private Long orgCode;
    private String programId;
    private int approvalReq;
    private int preApproveProc;
    private String preExecMethod;
    private String preProcessName;
    private int postApproveProc;
    private String postExecMethod;
    private String postProcessName;
    private int isTranPgm;
    private List<AuthLevelDTO> authLevels;
}
