package com.bbots.mfin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoanRepaymentScheduleDTO {

    @JsonProperty("loan_account_no")
    private String loanAccountNo;

    @JsonProperty("installment_no")
    private Integer installmentNo;

    @JsonProperty("due_date")
    private String dueDate;

    @JsonProperty("principal_due")
    private Double principalDue;

    @JsonProperty("interest_due")
    private Double interestDue;

    @JsonProperty("total_due")
    private Double totalDue;

    @JsonProperty("principal_paid")
    private Double principalPaid;

    @JsonProperty("interest_paid")
    private Double interestPaid;

    @JsonProperty("installment_status")
    private String installmentStatus;

    public LoanRepaymentScheduleDTO() {
    }

    public LoanRepaymentScheduleDTO(String loanAccountNo, Integer installmentNo, String dueDate, Double principalDue,
            Double interestDue, Double totalDue, Double principalPaid, Double interestPaid,
            String installmentStatus) {
        this.loanAccountNo = loanAccountNo;
        this.installmentNo = installmentNo;
        this.dueDate = dueDate;
        this.principalDue = principalDue;
        this.interestDue = interestDue;
        this.totalDue = totalDue;
        this.principalPaid = principalPaid;
        this.interestPaid = interestPaid;
        this.installmentStatus = installmentStatus;
    }

    public String getLoanAccountNo() {
        return loanAccountNo;
    }

    public void setLoanAccountNo(String loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public Integer getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(Integer installmentNo) {
        this.installmentNo = installmentNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Double getPrincipalDue() {
        return principalDue;
    }

    public void setPrincipalDue(Double principalDue) {
        this.principalDue = principalDue;
    }

    public Double getInterestDue() {
        return interestDue;
    }

    public void setInterestDue(Double interestDue) {
        this.interestDue = interestDue;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public Double getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(Double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public Double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(Double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public String getInstallmentStatus() {
        return installmentStatus;
    }

    public void setInstallmentStatus(String installmentStatus) {
        this.installmentStatus = installmentStatus;
    }
}
