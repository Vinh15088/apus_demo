package com.apus.demo.service;

import com.apus.demo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayrollService {
    CommonDto createPayroll(PayrollDto payrollDto);
    PayrollDto getPayroll(Long id);
    Page<PayrollListDto> getListPayrolls(PayrollSearchCriteria criteria, Pageable pageable);
    CommonDto updatePayroll(PayrollDto payrollDto);
    void deletePayroll(Long id);
}
