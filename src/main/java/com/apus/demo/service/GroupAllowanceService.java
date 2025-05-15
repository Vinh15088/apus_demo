package com.apus.demo.service;

import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.dto.GroupAllowanceSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GroupAllowanceService {
    Long addGroupAllowance(GroupAllowanceDto groupAllowanceDto);
    GroupAllowanceDto getGroupAllowance(Long id);
    Page<GroupAllowanceDto> searchGroupAllowances(GroupAllowanceSearchCriteria criteria, Pageable pageable);
    void updateGroupAllowance(GroupAllowanceDto groupAllowanceDto);
    void deleteGroupAllowance(Long id);
}
