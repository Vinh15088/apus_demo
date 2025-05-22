package com.apus.demo.repository;

import com.apus.demo.entity.RewardPolicyLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardPolicyLineRepository extends JpaRepository<RewardPolicyLineEntity, Long> {

    List<RewardPolicyLineEntity> findAllByRewardPolicyId(Long rewardPolicyId);
    void deleteAllByRewardPolicyId(Long rewardPolicyId);
}
