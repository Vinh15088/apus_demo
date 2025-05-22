package com.apus.demo.service;

import com.apus.demo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AllowancePolicyService {
    CommonDto createAllowancePolicy(AllowancePolicyDto allowancePolicyDto);
    AllowancePolicyDto getAllowancePolicy(Long id);
    Page<AllowancePolicyListDto> getListAllowancePolicy(AllowancePolicySearchCriteria criteria, Pageable pageable);
    CommonDto updateAllowancePolicy(AllowancePolicyDto allowancePolicyDto);
    void deleteAllowancePolicy(Long id);
}
