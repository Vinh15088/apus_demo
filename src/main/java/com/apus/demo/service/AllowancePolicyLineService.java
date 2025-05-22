package com.apus.demo.service;

import com.apus.demo.dto.AllowancePolicyLineDto;

import java.util.List;

public interface AllowancePolicyLineService {
    List<AllowancePolicyLineDto> getAllowancePolicyLines(Long allowancePolicyId);
    void createOrUpdateAllowancePolicyLines(Long allowancePolicyId, List<AllowancePolicyLineDto> policyLinesDto);
    void deleteAllowancePolicyLines(Long allowancePolicyId);
}
