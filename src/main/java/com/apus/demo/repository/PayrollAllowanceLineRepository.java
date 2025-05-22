package com.apus.demo.repository;

import com.apus.demo.entity.PayrollAllowanceLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollAllowanceLineRepository extends JpaRepository<PayrollAllowanceLineEntity, Long> {

    List<PayrollAllowanceLineEntity> findAllByPayrollId(Long payrollId);
    void deleteAllByPayrollId(Long payrollId);
}
