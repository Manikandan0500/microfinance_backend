package com.bbots.mfin.service;

import com.bbots.mfin.model.GroupMaster;
import com.bbots.mfin.model.GroupMasterId;
import com.bbots.mfin.dto.ResponseDTO;
import java.util.List;
import java.util.Optional;

public interface GroupMasterService {
    List<GroupMaster> getAll(Long orgCode);
    Optional<GroupMaster> getById(GroupMasterId id);
    ResponseDTO<GroupMaster> create(GroupMaster groupMaster);
    ResponseDTO<GroupMaster> update(GroupMasterId id, GroupMaster groupMaster);
}
