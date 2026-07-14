package com.bbots.mfin.dto;
 
import java.time.LocalDate;
 
public class Auth101 {
 
    private Auth101Id id;
    
    private Short approvalReq;
    private Short preApproveProc;
    private String preExecMethod;
    private String preProcessName;
    private Short postApproveProc;
    private String postExecMethod;
    private String postProcessName;
    private Short isTranPgm;
    
    private String eUser;
    private LocalDate eDate;
    private String cUser;
    private LocalDate cDate;
    private String aUser;
    private LocalDate aDate;
    private Long orgCode;
 
    public Long getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(Long orgCode) {
		this.orgCode = orgCode;
	}

	public Auth101() {}
 
    public Auth101Id getId() {
        return id;
    }
 
    public void setId(Auth101Id id) {
        this.id = id;
    }
 
    public Short getApprovalReq() {
        return approvalReq;
    }
 
    public void setApprovalReq(Short approvalReq) {
        this.approvalReq = approvalReq;
    }
 
    public Short getPreApproveProc() {
        return preApproveProc;
    }
 
    public void setPreApproveProc(Short preApproveProc) {
        this.preApproveProc = preApproveProc;
    }
 
    public String getPreExecMethod() {
        return preExecMethod;
    }
 
    public void setPreExecMethod(String preExecMethod) {
        this.preExecMethod = preExecMethod;
    }
 
    public String getPreProcessName() {
        return preProcessName;
    }
 
    public void setPreProcessName(String preProcessName) {
        this.preProcessName = preProcessName;
    }
 
    public Short getPostApproveProc() {
        return postApproveProc;
    }
 
    public void setPostApproveProc(Short postApproveProc) {
        this.postApproveProc = postApproveProc;
    }
 
    public String getPostExecMethod() {
        return postExecMethod;
    }
 
    public void setPostExecMethod(String postExecMethod) {
        this.postExecMethod = postExecMethod;
    }
 
    public String getPostProcessName() {
        return postProcessName;
    }
 
    public void setPostProcessName(String postProcessName) {
        this.postProcessName = postProcessName;
    }
 
    public Short getIsTranPgm() {
        return isTranPgm;
    }
 
    public void setIsTranPgm(Short isTranPgm) {
        this.isTranPgm = isTranPgm;
    }
 
    public String geteUser() {
        return eUser;
    }
 
    public void seteUser(String eUser) {
        this.eUser = eUser;
    }
 
    public LocalDate geteDate() {
        return eDate;
    }
 
    public void seteDate(LocalDate eDate) {
        this.eDate = eDate;
    }
 
    public String getcUser() {
        return cUser;
    }
 
    public void setcUser(String cUser) {
        this.cUser = cUser;
    }
 
    public LocalDate getcDate() {
        return cDate;
    }
 
    public void setcDate(LocalDate cDate) {
        this.cDate = cDate;
    }
 
    public String getaUser() {
        return aUser;
    }
 
    public void setaUser(String aUser) {
        this.aUser = aUser;
    }
 
    public LocalDate getaDate() {
        return aDate;
    }
 
    public void setaDate(LocalDate aDate) {
        this.aDate = aDate;
    }


    
    
}