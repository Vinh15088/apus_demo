package com.apus.demo.service;

import com.apus.demo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RewardPolicyService {
    CommonDto createRewardPolicy(RewardPolicyDto rewardPolicyDto);
    RewardPolicyDto getRewardPolicy(Long id);
    Page<RewardPolicyListDto> getListRewardPolicy(RewardPolicySearchCriteria criteria, Pageable pageable);
    CommonDto updateRewardPolicy(RewardPolicyDto rewardPolicyDto);
    void deleteRewardPolicy(Long id);
}
