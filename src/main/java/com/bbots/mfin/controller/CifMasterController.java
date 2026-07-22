package com.bbots.mfin.controller;

import com.bbots.mfin.model.CifMaster;
import com.bbots.mfin.service.CifMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cif-master")
@CrossOrigin(origins = "*")
public class CifMasterController {

    @Autowired
    private CifMasterService cifMasterService;

    @GetMapping
    public List<CifMaster> getAll(@RequestParam(value = "orgCode", required = false) Long orgCode) {
        return cifMasterService.getAll(orgCode);
    }
}
