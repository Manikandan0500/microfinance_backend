package com.bbots.mfin.controller;

import com.bbots.mfin.model.LoanApplication;
import com.bbots.mfin.service.LoanApplicationService;
import com.bbots.mfin.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-application")
@CrossOrigin(origins = "*")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @GetMapping
    public List<LoanApplication> getAll() {
        return loanApplicationService.getAll();
    }

    @PostMapping
    public ResponseDTO<LoanApplication> create(@RequestBody LoanApplication app) {
        return loanApplicationService.create(app);
    }

    @PutMapping
    public ResponseDTO<LoanApplication> update(@RequestBody LoanApplication app) {
        return loanApplicationService.update(app);
    }

    @DeleteMapping("/{orgCode}/{queueId}")
    public void delete(@PathVariable Long orgCode, @PathVariable String queueId) {
        loanApplicationService.delete(orgCode, queueId);
    }
}
