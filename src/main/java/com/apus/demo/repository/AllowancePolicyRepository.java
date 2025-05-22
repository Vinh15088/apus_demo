package com.apus.demo.repository;

import com.apus.demo.entity.AllowancePolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowancePolicyRepository extends JpaRepository<AllowancePolicyEntity, Long>,
        JpaSpecificationExecutor<AllowancePolicyEntity> {

}
