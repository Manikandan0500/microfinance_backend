package com.bbots.mfin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDataBlock {
    private Long orgCode;
    private Date effDate;
    private String programId;
    private String primaryKey;
    private Long authSl;
    private int recSl;
    private String tableName;
    private String dataBlock;
}
