package com.apus.demo.repository;

import com.apus.demo.entity.PayrollRewardLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRewardLineRepository extends JpaRepository<PayrollRewardLineEntity, Long> {

    List<PayrollRewardLineEntity> findAllByPayrollId(Long payrollId);

    void deleteAllByPayrollId(Long payrollId);
}
