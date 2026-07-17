package com.bbots.mfin.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordPolicyDto {

    private Long orgcode; // Org Code
    private String orgName; // From org001
    private Integer status; // From org001

    // Password Rules
    @JsonProperty("min_length")
    private Integer minLength;

    @JsonProperty("max_length")
    private Integer maxLength;

    @JsonProperty("require_uppercase")
    private Boolean requireUppercase;

    @JsonProperty("require_lowercase")
    private Boolean requireLowercase;

    @JsonProperty("require_number")
    private Boolean requireNumber;

    @JsonProperty("require_special_char")
    private Boolean requireSpecialChar;

    @JsonProperty("password_expiry_days")
    private Integer expiryDays;

    @JsonProperty("max_failed_attempts")
    private Integer maxFailedAttempts;

    @JsonProperty("password_history_count")
    private Integer historyCount;

    // Audit fields
    private String euser;
    private LocalDateTime edate;
    private String auser;
    private LocalDateTime adate;
    private String cuser;
    private LocalDateTime cdate;
  private String pgmId;
  private String userName;
  
    // Constructor
    public PasswordPolicyDto() {}

    // Getters & Setters



    public String getOrgName() { return orgName; }
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

	public Long getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(Long orgcode) {
		this.orgcode = orgcode;
	}

	public void setOrgName(String orgName) { this.orgName = orgName; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getMinLength() { return minLength; }
    public void setMinLength(Integer minLength) { this.minLength = minLength; }

    public Integer getMaxLength() { return maxLength; }
    public void setMaxLength(Integer maxLength) { this.maxLength = maxLength; }

    public Boolean getRequireUppercase() { return requireUppercase; }
    public void setRequireUppercase(Boolean requireUppercase) { this.requireUppercase = requireUppercase; }

    public Boolean getRequireLowercase() { return requireLowercase; }
    public void setRequireLowercase(Boolean requireLowercase) { this.requireLowercase = requireLowercase; }

    public Boolean getRequireNumber() { return requireNumber; }
    public void setRequireNumber(Boolean requireNumber) { this.requireNumber = requireNumber; }

    public Boolean getRequireSpecialChar() { return requireSpecialChar; }
    public void setRequireSpecialChar(Boolean requireSpecialChar) { this.requireSpecialChar = requireSpecialChar; }

    public Integer getExpiryDays() { return expiryDays; }
    public void setExpiryDays(Integer expiryDays) { this.expiryDays = expiryDays; }

    public Integer getMaxFailedAttempts() { return maxFailedAttempts; }
    public void setMaxFailedAttempts(Integer maxFailedAttempts) { this.maxFailedAttempts = maxFailedAttempts; }

    public Integer getHistoryCount() { return historyCount; }
    public void setHistoryCount(Integer historyCount) { this.historyCount = historyCount; }

    public String getEuser() { return euser; }
    public void setEuser(String euser) { this.euser = euser; }

    public LocalDateTime getEdate() { return edate; }
    public void setEdate(LocalDateTime edate) { this.edate = edate; }

    public String getAuser() { return auser; }
    public void setAuser(String auser) { this.auser = auser; }

    public LocalDateTime getAdate() { return adate; }
    public void setAdate(LocalDateTime adate) { this.adate = adate; }

    public String getCuser() { return cuser; }
    public void setCuser(String cuser) { this.cuser = cuser; }

    public LocalDateTime getCdate() { return cdate; }
    public void setCdate(LocalDateTime cdate) { this.cdate = cdate; }

    @Override
    public String toString() {
        return "PasswordPolicyDto{" +
                "syscode='" + orgcode + '\'' +
                ", orgName='" + orgName + '\'' +
                ", status=" + status +
                ", minLength=" + minLength +
                ", maxLength=" + maxLength +
                ", requireUppercase=" + requireUppercase +
                ", requireLowercase=" + requireLowercase +
                ", requireNumber=" + requireNumber +
                ", requireSpecialChar=" + requireSpecialChar +
                ", expiryDays=" + expiryDays +
                ", maxFailedAttempts=" + maxFailedAttempts +
                ", historyCount=" + historyCount +
                '}';
    }
}