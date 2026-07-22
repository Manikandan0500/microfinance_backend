package com.bbots.mfin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CifMaster {
    private CifMasterId id;
    private String custtype;
    private String title;
    private String firstname;
    private String middlename;
}
