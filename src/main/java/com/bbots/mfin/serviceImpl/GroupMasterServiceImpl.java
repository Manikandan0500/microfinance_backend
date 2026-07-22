package com.bbots.mfin.serviceImpl;

import com.bbots.mfin.model.GroupMaster;
import com.bbots.mfin.model.GroupMasterId;
import com.bbots.mfin.repository.GroupMasterRepository;
import com.bbots.mfin.service.GroupMasterService;
import com.bbots.mfin.service.AuthorizationProcedureService;
import com.bbots.mfin.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupMasterServiceImpl implements GroupMasterService {

    @Autowired
    private GroupMasterRepository groupMasterRepository;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    @Override
    public List<GroupMaster> getAll(Long orgCode) {
        if (orgCode != null) {
            return groupMasterRepository.findByOrgCode(orgCode);
        }
        return groupMasterRepository.findAll();
    }

    @Override
    public Optional<GroupMaster> getById(GroupMasterId id) {
        return groupMasterRepository.findById(id);
    }

    @Override
    public ResponseDTO<GroupMaster> create(GroupMaster groupMaster) {
        ResponseDTO<GroupMaster> response = new ResponseDTO<>();
        try {
            if (groupMasterRepository.existsById(groupMaster.getId())) {
                throw new IllegalArgumentException("GroupMaster already exists with given ID");
            }
            groupMaster.setEUser(groupMaster.getUserName() != null && !groupMaster.getUserName().isEmpty() ? groupMaster.getUserName() : "SYS");
            groupMaster.setEDate(LocalDateTime.now());
            
            authProcedureService.processAuthorization(groupMaster.getId().getOrgcode(), "CLIENTGRP", "group001", groupMaster, "INSERT");
            
            response.setSuccess(true);
            response.setMessage("Sent for authorization");
            response.setData(groupMaster);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO<GroupMaster> update(GroupMasterId id, GroupMaster groupMaster) {
        ResponseDTO<GroupMaster> response = new ResponseDTO<>();
        try {
            Optional<GroupMaster> existingOpt = groupMasterRepository.findById(id);
            if (existingOpt.isPresent()) {
                GroupMaster existing = existingOpt.get();
                existing.setGroupName(groupMaster.getGroupName());
                existing.setBranchCode(groupMaster.getBranchCode());
                existing.setRegionCode(groupMaster.getRegionCode());
                existing.setRegionalOfficerId(groupMaster.getRegionalOfficerId());
                existing.setSourceSystem(groupMaster.getSourceSystem());
                existing.setSourceRefNo(groupMaster.getSourceRefNo());
                existing.setMeetingDay(groupMaster.getMeetingDay());
                existing.setMeetingFrequency(groupMaster.getMeetingFrequency());
                existing.setGroupStatus(groupMaster.getGroupStatus());
                
                existing.setCUser(groupMaster.getUserName() != null && !groupMaster.getUserName().isEmpty() ? groupMaster.getUserName() : "SYS");
                existing.setCDate(LocalDateTime.now());
                
                authProcedureService.processAuthorization(existing.getId().getOrgcode(), "CLIENTGRP", "group001", existing, "UPDATE");
                
                response.setSuccess(true);
                response.setMessage("Sent for authorization");
                response.setData(existing);
            } else {
                response.setSuccess(false);
                response.setMessage("GroupMaster not found");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
