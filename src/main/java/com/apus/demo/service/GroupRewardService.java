package com.apus.demo.service;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.dto.GroupRewardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface GroupRewardService {
    CommonDto createGroupReward(GroupRewardDto groupRewardDto);
    GroupRewardDto getGroupReward(Long id);
    Page<GroupRewardDto> getListGroupRewards(CommonSearchCriteria criteria, Pageable pageable);
    List<GroupRewardDto> getGroupAllowanceEntityByIds(Set<Long> ids);
    CommonDto updateGroupReward(GroupRewardDto groupRewardDto);
    void deleteGroupReward(Long id);
}
