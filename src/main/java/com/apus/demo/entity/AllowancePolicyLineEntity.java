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
@Table(name = "allowance_policy_line")
public class AllowancePolicyLineEntity extends AbstractEntity<Long> {

    @Column(name = "allowance_policy_id", nullable = false)
    private Long allowancePolicyId;

    @Column(name = "allowance_id", nullable = false)
    private Long allowanceId;

    @Column(name = "cycle", nullable = false)
    @Enumerated(EnumType.STRING)
    private Cycle cycle;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}
