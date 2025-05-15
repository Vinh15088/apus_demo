package com.apus.demo.service;

import com.apus.demo.dto.GroupRewardDto;
import com.apus.demo.dto.GroupRewardSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupRewardService {
    Object addGroupReward(GroupRewardDto groupRewardDto);
    GroupRewardDto getGroupReward(Long id);
    Page<GroupRewardDto> searchGroupRewards(GroupRewardSearchCriteria criteria, Pageable pageable);
    Object updateGroupReward(GroupRewardDto groupRewardDto);
    void deleteGroupReward(Long id);
}
