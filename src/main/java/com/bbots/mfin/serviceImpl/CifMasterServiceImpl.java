package com.bbots.mfin.serviceImpl;

import com.bbots.mfin.model.CifMaster;
import com.bbots.mfin.repository.CifMasterRepository;
import com.bbots.mfin.service.CifMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CifMasterServiceImpl implements CifMasterService {

    @Autowired
    private CifMasterRepository cifMasterRepository;

    @Override
    public List<CifMaster> getAll(Long orgCode) {
        if (orgCode != null) {
            return cifMasterRepository.findByOrgCode(orgCode);
        }
        return cifMasterRepository.findAll();
    }
}
