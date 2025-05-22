package com.apus.demo.service;

import com.apus.demo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface AllowanceService {

    CommonDto createAllowance(AllowanceDto allowanceDto);
    AllowanceDto getAllowance(Long id);
    Page<AllowanceListDto> getListAllowances(CommonSearchCriteria criteria, Pageable pageable);
    List<AllowanceDto> getMapAllowanceEntityByIds(Set<Long> ids);
    CommonDto updateAllowance(AllowanceDto allowanceDto);
    void deleteAllowance(Long id);
}
