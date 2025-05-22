package com.apus.demo.entity;

import com.apus.demo.util.enums.ApplicableType;
import com.apus.demo.util.enums.PolicyType;
import com.apus.demo.util.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "allowance_policy")
public class AllowancePolicyEntity extends AbstractEntity<Long> {

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "name", length = 55, nullable = false)
    private String name;

    @Column(name = "description" )
    private String description;

    @Column(name = "type", length = 55)
    @Enumerated(EnumType.STRING)
    private PolicyType type;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "applicable_type", length = 55, nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicableType applicableType;

    @Column(name = "state", length = 55)
    @Enumerated(EnumType.STRING)
    private State state;
}
