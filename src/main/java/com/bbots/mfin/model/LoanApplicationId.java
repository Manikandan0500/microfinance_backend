package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationId implements Serializable {
    private Long orgCode;
    private String queueId;
}
