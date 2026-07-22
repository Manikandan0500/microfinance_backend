package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    @JsonProperty("orgcode")
    private Long orgCode;
    @JsonProperty("queue_id")
    private String queueId;
    @JsonProperty("source_system")
    private String sourceSystem;
    @JsonProperty("source_ref_no")
    private String sourceRefNo;
    @JsonProperty("currency_code")
    private String currencyCode;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("group_code")
    private String groupCode;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("approved_amount")
    private Double approvedAmount;
    @JsonProperty("approved_tenure_months")
    private Integer approvedTenureMonths;
    @JsonProperty("approved_interest_rate")
    private Double approvedInterestRate;
    @JsonProperty("queue_date")
    private LocalDate queueDate;
    @JsonProperty("assigned_to_user_id")
    private String assignedToUserId;
    @JsonProperty("disbursement_status")
    private String disbursementStatus;
    
    // Audit fields
    @JsonProperty("euser")
    private String eUser;
    @JsonProperty("edate")
    private LocalDateTime eDate;
    @JsonProperty("auser")
    private String aUser;
    @JsonProperty("adate")
    private LocalDateTime aDate;
    @JsonProperty("cuser")
    private String cUser;
    @JsonProperty("cdate")
    private LocalDateTime cDate;
}
