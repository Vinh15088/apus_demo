package com.apus.demo.service;

import com.apus.demo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface RewardService {
    CommonDto createReward(RewardDto rewardDto);
    RewardDto getReward(Long id);
    Page<RewardListDto> getListRewards(CommonSearchCriteria criteria, Pageable pageable);
    List<RewardDto> getMapRewardEntityByIds(Set<Long> ids);
    CommonDto updateReward(RewardDto rewardDto);
    void deleteReward(Long id);
}
