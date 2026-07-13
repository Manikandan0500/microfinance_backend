package com.bbots.mfin.dto;

import lombok.Data;
import java.util.List;

@Data
public class JournalRequestDTO {
    private HeaderDTO header;
    private List<JournalDetailDTO> details;

    @Data
    public static class HeaderDTO {
        private Long orgcode;
        private Long brncd;
        private String trandate;
        private String narr;
        private String eUser;
        private Integer tranid;
        private Double totaldebit;
        private Double totalcredit;
        private String basecurr;
    }

    @Data
    public static class JournalDetailDTO {
        private Long orgcode;
        private Long brncd;
        private String trandate;
        private Integer tranid;
        private Integer legid;
        private Long acnum;
        private String actype;
        private Double trandbamt;
        private Double trancramt;
        private String extrefno;
        private String euser;
        private String trancurr;
        private String basecurr;
        private Double convrate;
        private String value_date;
        private Double basedbamt;
        private Double basecramt;
        private String trancode;
        private String trantype;
        private String glacc_code;
    }
}
