package com.apus.demo.service;

import com.apus.demo.dto.PayrollAllowanceLineDto;

import java.util.List;

public interface PayrollAllowanceLineService {

    List<PayrollAllowanceLineDto> getPayrollAllowanceLines(Long payrollId);
    void createOrUpdatePayrollAllowanceLines(Long payrollId, List<PayrollAllowanceLineDto> allowanceLinesDto);
    void deletePayrollAllowanceLines(Long payrollId);
}
