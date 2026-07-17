package com.bbots.mfin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.AuthDataBlock;
import com.bbots.mfin.dto.AuthRecord;
import com.bbots.mfin.dto.UserProfileDTO;
import com.bbots.mfin.model.User;
import com.bbots.mfin.repository.AuthRepository;
import com.bbots.mfin.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Map<String, Object> getPaginatedUsers(int page, int size) {
        int limit = size;
        int offset = page * size;

        List<User> users = repository.findAll(limit, offset);
        long totalElements = repository.count();

        Map<String, Object> response = new HashMap<>();
        response.put("content", users);
        response.put("totalElements", totalElements);

        return response;
    }

    public User getUserById(Long orgcode, String userscd) {
        return repository.findById(orgcode, userscd);
    }

    public void createUser(User user) {
        repository.save(user);
    }

    public void createUserAuthRequest(User user) {
        try {
            String userJson = objectMapper.writeValueAsString(user);

            AuthRecord authRecord = new AuthRecord();
            authRecord.setOrgCode(user.getOrgcode());
            authRecord.setProgramId("AUTH002"); // Adjust your Program ID appropriately
            authRecord.setDisplayRemarks("User Creation Request for " + user.getUserscd());
            authRecord.setEntryUser(user.getEuser());

            AuthDataBlock block = new AuthDataBlock();
            block.setOrgCode(user.getOrgcode());
            block.setEffDate(new Date());
            block.setProgramId("AUTH002");
            block.setPrimaryKey(user.getUserscd());
            block.setRecSl(1);
            block.setTableName("USERS");
            block.setDataBlock(userJson);

            authRecord.setDataBlocks(Collections.singletonList(block));
            authRepository.insertAuthRequest(authRecord);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initiate user creation process", e);
        }
    }

    public void updateUser(User user) {
        repository.update(user);
    }

    public void deleteUser(Long orgcode, String userscd) {
        repository.delete(orgcode, userscd);
    }

    public UserProfileDTO getUserProfileByUsername(String username) {

        Object[] data = repository.getUserProfileByUsername(username);

        if (data == null) {
            throw new RuntimeException("User not found for email: " + username);
        }

        UserProfileDTO dto = new UserProfileDTO();
        dto.setUsername((String) data[0]);
        dto.setEmail((String) data[1]);
        dto.setRole((String) data[2]);

        return dto;
    }
}
