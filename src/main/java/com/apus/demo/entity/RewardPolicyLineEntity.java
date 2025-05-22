package com.apus.demo.entity;

import com.apus.demo.util.enums.Cycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reward_policy_line")
public class RewardPolicyLineEntity extends AbstractEntity<Long> {
    @Column(name = "reward_policy_id", nullable = false)
    private Long rewardPolicyId;

    @Column(name = "reward_id", nullable = false)
    private Long rewardId;

    @Column(name = "cycle", nullable = false)
    @Enumerated(EnumType.STRING)
    private Cycle cycle;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}
