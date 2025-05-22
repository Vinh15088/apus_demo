package com.apus.demo.entity;

import com.apus.demo.util.enums.AmountItem;
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
@Table(name = "payroll_reward_line")
public class PayrollRewardLineEntity extends AbstractEntity<Long> {

    @Column(name = "payroll_id", nullable = false)
    private Long payrollId;

    @Column(name = "reward_group_id", nullable = false)
    private Long rewardGroupId;

    @Column(name = "reward_id", nullable = false)
    private Long rewardId;

    @Column(name = "amount_item", nullable = false)
    @Enumerated(EnumType.STRING)
    private AmountItem amountItem;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "taxable_amount", nullable = false)
    private BigDecimal taxableAmount;

    @Column(name = "insurance_amount", nullable = false)
    private BigDecimal insuranceAmount;
}
