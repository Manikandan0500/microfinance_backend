package com.bbots.mfin.service;

import com.bbots.mfin.model.GroupMemberMap;
import com.bbots.mfin.model.GroupMemberMapId;
import com.bbots.mfin.dto.ResponseDTO;
import java.util.List;
import java.util.Optional;

public interface GroupMemberMapService {
    List<GroupMemberMap> getAll(Long orgCode, String groupCode);
    Optional<GroupMemberMap> getById(GroupMemberMapId id);
    ResponseDTO<GroupMemberMap> create(GroupMemberMap groupMemberMap);
    ResponseDTO<GroupMemberMap> update(GroupMemberMapId id, GroupMemberMap groupMemberMap);
}
