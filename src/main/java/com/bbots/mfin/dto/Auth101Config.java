package com.bbots.mfin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth101Config {
    private String id;
    private String name;
    private boolean approvalReq;
    private boolean preApproveProc;
    private String preExecMethod;
    private String preProcessName;
    private boolean postApproveProc;
    private String postExecMethod;
    private String postProcessName;
    private boolean isTran;
    private int levels;
}
