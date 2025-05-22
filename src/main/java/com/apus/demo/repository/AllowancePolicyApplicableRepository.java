package com.apus.demo.repository;

import com.apus.demo.entity.AllowancePolicyApplicableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowancePolicyApplicableRepository extends JpaRepository<AllowancePolicyApplicableEntity, Long>,
        JpaSpecificationExecutor<AllowancePolicyApplicableEntity> {

    AllowancePolicyApplicableEntity findByAllowancePolicyId(Long id);
    void deleteByAllowancePolicyId(Long id);

}
