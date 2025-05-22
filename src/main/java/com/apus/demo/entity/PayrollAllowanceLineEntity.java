package com.apus.demo.entity;

import com.apus.demo.util.enums.AmountItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "payroll_allowance_line")
public class PayrollAllowanceLineEntity extends AbstractEntity<Long> {

    @Column(name = "payroll_id", nullable = false)
    private Long payrollId;

    @Column(name = "allowance_group_id")
    private Long allowanceGroupId;

    @Column(name = "allowance_id", nullable = false)
    private Long allowanceId;

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
