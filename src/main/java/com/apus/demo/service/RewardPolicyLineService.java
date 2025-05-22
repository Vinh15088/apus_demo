package com.apus.demo.service;

import com.apus.demo.dto.RewardPolicyLineDto;

import java.util.List;

public interface RewardPolicyLineService {
    List<RewardPolicyLineDto> getRewardPolicyLines(Long rewardPolicyId);
    void createOrUpdateRewardPolicyLines(Long rewardPolicyId, List<RewardPolicyLineDto> policyLinesDto);
    void deleteRewardPolicyLines(Long rewardPolicyId);
}
