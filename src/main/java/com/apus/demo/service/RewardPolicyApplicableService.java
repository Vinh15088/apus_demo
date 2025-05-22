package com.apus.demo.service;

import com.apus.demo.dto.RewardPolicyApplicableDto;

public interface RewardPolicyApplicableService {
    RewardPolicyApplicableDto getRewardPolicyApplicable(Long rewardPolicyId, String applicableType);
    void createRewardPolicyApplicable(Long policyId, Long targetId);
    void updateRewardPolicyApplicable(Long rewardPolicyId, String applicableType, Long applicableTargetId);
    void deleteRewardPolicyApplicable(Long rewardPolicyId);
}
