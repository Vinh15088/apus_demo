package com.apus.demo.service;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.dto.GroupAllowanceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;


public interface GroupAllowanceService {
    CommonDto createGroupAllowance(GroupAllowanceDto groupAllowanceDto);
    GroupAllowanceDto getGroupAllowance(Long id);
    Page<GroupAllowanceDto> getListGroupAllowances(CommonSearchCriteria criteria, Pageable pageable);
    List<GroupAllowanceDto> getGroupAllowancesByIds(Set<Long> ids);
    CommonDto updateGroupAllowance(GroupAllowanceDto groupAllowanceDto);
    void deleteGroupAllowance(Long id);
}
