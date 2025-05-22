package com.apus.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "allowance_policy_applicable")
public class AllowancePolicyApplicableEntity extends AbstractEntity<Long> {

    @Column(name = "allowance_policy_id", nullable = false)
    private Long allowancePolicyId;

    @Column(name = "target_id", nullable = false)
    private Long targetId;
}
