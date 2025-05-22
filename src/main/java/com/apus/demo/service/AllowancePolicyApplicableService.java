package com.apus.demo.service;


import com.apus.demo.dto.AllowancePolicyApplicableDto;

public interface AllowancePolicyApplicableService {
    AllowancePolicyApplicableDto getAllowancePolicyApplicable(Long allowancePolicyId, String applicableType);
    void createAllowancePolicyApplicable(Long policyId, Long targetId);
    void updateAllowancePolicyApplicable(Long allowancePolicyId, String applicableType, Long applicableTargetId);
    void deleteAllowancePolicyApplicable(Long allowancePolicyId);
}
