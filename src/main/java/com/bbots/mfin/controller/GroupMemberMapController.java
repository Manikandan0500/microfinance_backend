package com.bbots.mfin.controller;

import com.bbots.mfin.model.GroupMemberMap;
import com.bbots.mfin.model.GroupMemberMapId;
import com.bbots.mfin.service.GroupMemberMapService;
import com.bbots.mfin.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/group-member-map")
@CrossOrigin(origins = "*")
public class GroupMemberMapController {

    @Autowired
    private GroupMemberMapService groupMemberMapService;

    @GetMapping
    public List<GroupMemberMap> getAll(
            @RequestParam(value = "orgCode", required = false) Long orgCode,
            @RequestParam(value = "groupCode", required = false) String groupCode) {
        return groupMemberMapService.getAll(orgCode, groupCode);
    }

    @GetMapping("/{orgCode}/{groupCode}/{clientId}")
    public ResponseEntity<GroupMemberMap> getById(
            @PathVariable Long orgCode, 
            @PathVariable String groupCode,
            @PathVariable String clientId) {
        GroupMemberMapId id = new GroupMemberMapId(orgCode, groupCode, clientId);
        Optional<GroupMemberMap> map = groupMemberMapService.getById(id);
        return map.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<GroupMemberMap>> create(@RequestBody GroupMemberMap groupMemberMap) {
        if (groupMemberMap.getId() == null || 
            groupMemberMap.getId().getOrgcode() == null || 
            groupMemberMap.getId().getGroupCode() == null || 
            groupMemberMap.getId().getClientId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            ResponseDTO<GroupMemberMap> saved = groupMemberMapService.create(groupMemberMap);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).build(); // Conflict
        }
    }

    @PutMapping("/{orgCode}/{groupCode}/{clientId}")
    public ResponseEntity<ResponseDTO<GroupMemberMap>> update(
            @PathVariable Long orgCode, 
            @PathVariable String groupCode,
            @PathVariable String clientId,
            @RequestBody GroupMemberMap groupMemberMap) {
        
        GroupMemberMapId id = new GroupMemberMapId(orgCode, groupCode, clientId);
        ResponseDTO<GroupMemberMap> updated = groupMemberMapService.update(id, groupMemberMap);
        if (updated != null && updated.isSuccess()) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.badRequest().body(updated);
    }
}
