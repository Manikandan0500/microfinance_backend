package com.bbots.mfin.service;

import com.bbots.mfin.model.CifMaster;
import java.util.List;

public interface CifMasterService {
    List<CifMaster> getAll(Long orgCode);
}
