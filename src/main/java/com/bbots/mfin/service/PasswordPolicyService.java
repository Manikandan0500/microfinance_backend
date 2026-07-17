package com.bbots.mfin.service;

import java.util.List;
import java.util.Optional;

import com.bbots.mfin.dto.PasswordPolicyDto;


public interface PasswordPolicyService {

    public Optional<PasswordPolicyDto> getPasswordPolicy(Long orgcode);

    public List<PasswordPolicyDto> getAllPasswordPolicies();

    public PasswordPolicyDto savePasswordPolicy(PasswordPolicyDto policy);

    public void updatePasswordPolicy(PasswordPolicyDto policy);

    public void deletePasswordPolicy(Long org);

    public boolean passwordPolicyExists(Long orgcode);
    public PasswordPolicyDto getPolicyByOrgCode();

}