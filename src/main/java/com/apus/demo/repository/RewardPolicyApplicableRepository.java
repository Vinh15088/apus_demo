package com.apus.demo.repository;

import com.apus.demo.entity.RewardPolicyApplicableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardPolicyApplicableRepository extends JpaRepository<RewardPolicyApplicableEntity, Long> {

    RewardPolicyApplicableEntity findByRewardPolicyId(Long rewardPolicyId);
    void deleteByRewardPolicyId(Long rewardPolicyId);
}
