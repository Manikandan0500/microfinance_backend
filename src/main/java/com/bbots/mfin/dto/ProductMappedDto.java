package com.bbots.mfin.dto;

import java.time.LocalDateTime;

public class ProductMappedDto {
    private Long orgcode;
    private Long prodcode;
    private Boolean status;
    private String euser;
    private LocalDateTime edate;
    private String auser;
    private LocalDateTime adate;
    private String cuser;
    private LocalDateTime cdate;

    public Long getOrgcode() { return orgcode; }
    public void setOrgcode(Long orgcode) { this.orgcode = orgcode; }

    public Long getProdcode() { return prodcode; }
    public void setProdcode(Long prodcode) { this.prodcode = prodcode; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

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
}
