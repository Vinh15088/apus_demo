package com.apus.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reward_policy_applicable")
public class RewardPolicyApplicableEntity extends AbstractEntity<Long> {
    @Column(name = "reward_policy_id", nullable = false)
    private Long rewardPolicyId;

    @Column(name = "target_id", nullable = false)
    private Long targetId;
}
