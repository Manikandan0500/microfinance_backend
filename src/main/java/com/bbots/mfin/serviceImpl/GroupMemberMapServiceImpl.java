package com.bbots.mfin.serviceImpl;

import com.bbots.mfin.model.GroupMemberMap;
import com.bbots.mfin.model.GroupMemberMapId;
import com.bbots.mfin.repository.GroupMemberMapRepository;
import com.bbots.mfin.service.GroupMemberMapService;
import com.bbots.mfin.service.AuthorizationProcedureService;
import com.bbots.mfin.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupMemberMapServiceImpl implements GroupMemberMapService {

    @Autowired
    private GroupMemberMapRepository groupMemberMapRepository;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    @Override
    public List<GroupMemberMap> getAll(Long orgCode, String groupCode) {
        if (orgCode != null && groupCode != null) {
            return groupMemberMapRepository.findByOrgCodeAndGroupCode(orgCode, groupCode);
        }
        return groupMemberMapRepository.findAll();
    }

    @Override
    public Optional<GroupMemberMap> getById(GroupMemberMapId id) {
        return groupMemberMapRepository.findById(id);
    }

    @Override
    public ResponseDTO<GroupMemberMap> create(GroupMemberMap groupMemberMap) {
        ResponseDTO<GroupMemberMap> response = new ResponseDTO<>();
        try {
            if (groupMemberMapRepository.existsById(groupMemberMap.getId())) {
                throw new IllegalArgumentException("GroupMemberMap already exists with given ID");
            }
            groupMemberMap.setEUser(groupMemberMap.getUserName() != null && !groupMemberMap.getUserName().isEmpty() ? groupMemberMap.getUserName() : "SYS");
            groupMemberMap.setEDate(LocalDateTime.now());
            
            authProcedureService.processAuthorization(groupMemberMap.getId().getOrgcode(), "CLIENTGRPMAP", "group002", groupMemberMap, "INSERT");
            
            response.setSuccess(true);
            response.setMessage("Sent for authorization");
            response.setData(groupMemberMap);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO<GroupMemberMap> update(GroupMemberMapId id, GroupMemberMap groupMemberMap) {
        ResponseDTO<GroupMemberMap> response = new ResponseDTO<>();
        try {
            Optional<GroupMemberMap> existingOpt = groupMemberMapRepository.findById(id);
            if (existingOpt.isPresent()) {
                GroupMemberMap existing = existingOpt.get();
                existing.setMemberRole(groupMemberMap.getMemberRole());
                existing.setJoinDate(groupMemberMap.getJoinDate());
                existing.setMemberStatus(groupMemberMap.getMemberStatus());
                
                existing.setCUser(groupMemberMap.getUserName() != null && !groupMemberMap.getUserName().isEmpty() ? groupMemberMap.getUserName() : "SYS");
                existing.setCDate(LocalDateTime.now());
                
                authProcedureService.processAuthorization(existing.getId().getOrgcode(), "CLIENTGRPMAP", "group002", existing, "UPDATE");
                
                response.setSuccess(true);
                response.setMessage("Sent for authorization");
                response.setData(existing);
            } else {
                response.setSuccess(false);
                response.setMessage("GroupMemberMap not found");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
