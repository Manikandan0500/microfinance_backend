package com.bbots.mfin.dto;

import java.time.LocalDateTime;

public class BranchValidationRequestDto {
	
	  private Long orgCode;
	    private Long brncd;
	    private LocalDateTime branchModDate;
		public Long getOrgCode() {
			return orgCode;
		}
		public void setOrgCode(Long orgCode) {
			this.orgCode = orgCode;
		}
		public Long getBrncd() {
			return brncd;
		}
		public void setBrncd(Long brncd) {
			this.brncd = brncd;
		}
		public LocalDateTime getBranchModDate() {
			return branchModDate;
		}
		public void setBranchModDate(LocalDateTime branchModDate) {
			this.branchModDate = branchModDate;
		}
	    
	    
 
}
