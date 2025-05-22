package com.apus.demo.repository;

import com.apus.demo.entity.RewardPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardPolicyRepository extends JpaRepository<RewardPolicyEntity, Long>,
        JpaSpecificationExecutor<RewardPolicyEntity> {
}
