package com.bbots.mfin.controller;

import com.bbots.mfin.model.GroupMaster;
import com.bbots.mfin.model.GroupMasterId;
import com.bbots.mfin.service.GroupMasterService;
import com.bbots.mfin.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/group-master")
@CrossOrigin(origins = "*")
public class GroupMasterController {

    @Autowired
    private GroupMasterService groupMasterService;

    @GetMapping
    public List<GroupMaster> getAll(@RequestParam(value = "orgCode", required = false) Long orgCode) {
        return groupMasterService.getAll(orgCode);
    }

    @GetMapping("/{orgCode}/{groupCode}")
    public ResponseEntity<GroupMaster> getById(@PathVariable Long orgCode, @PathVariable String groupCode) {
        GroupMasterId id = new GroupMasterId(orgCode, groupCode);
        Optional<GroupMaster> group = groupMasterService.getById(id);
        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<GroupMaster>> create(@RequestBody GroupMaster groupMaster) {
        if (groupMaster.getId() == null || groupMaster.getId().getOrgcode() == null || groupMaster.getId().getGroupCode() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            ResponseDTO<GroupMaster> saved = groupMasterService.create(groupMaster);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).build(); // Conflict
        }
    }

    @PutMapping("/{orgCode}/{groupCode}")
    public ResponseEntity<ResponseDTO<GroupMaster>> update(@PathVariable Long orgCode, @PathVariable String groupCode, @RequestBody GroupMaster groupMaster) {
        GroupMasterId id = new GroupMasterId(orgCode, groupCode);
        ResponseDTO<GroupMaster> updated = groupMasterService.update(id, groupMaster);
        if (updated != null && updated.isSuccess()) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.badRequest().body(updated);
        }
    }
}
