package com.apus.demo.service;

import com.apus.demo.dto.PayrollRewardLineDto;

import java.util.List;

public interface PayrollRewardLineService {
    List<PayrollRewardLineDto> getPayrollRewardLines(Long payrollId);
    void createOrUpdatePayrollRewardLines(Long payrollId, List<PayrollRewardLineDto> rewardLinesDto);
    void deletePayrollRewardLines(Long payrollId);
}
