package com.apus.demo.repository;

import com.apus.demo.entity.AllowancePolicyLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllowancePolicyLineRepository extends JpaRepository<AllowancePolicyLineEntity, Long>{

    List<AllowancePolicyLineEntity> findAllByAllowancePolicyId(Long allowancePolicyId);
    void deleteAllByAllowancePolicyId(Long allowancePolicyId);
}
