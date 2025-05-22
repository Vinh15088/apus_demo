package com.apus.demo.dto;

import com.apus.demo.dto.validator.EnumValue;
import com.apus.demo.util.enums.Cycle;
import com.apus.demo.util.enums.PayrollType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollDto {

    private Long id;

    @NotNull(message = "employee must be not null")
    private CommonDto employee;

    @NotNull(message = "department must be not null")
    private CommonDto department;

    @NotNull(message = "position must be not null")
    private CommonDto position;

    @NotNull(message = "payrollType must be not null")
    @EnumValue(name = "payrollType", enumClass = PayrollType.class)
    private String payrollType;

    @NotNull(message = "cycle must be not null")
    @EnumValue(name = "cycle", enumClass = Cycle.class)
    private String cycle;

    @NotNull(message = "startDate must be not null")
    private LocalDateTime startDate;

    @NotNull(message = "amountItem must be not null")
    private BigDecimal totalAmount;

    private String note;

    private List<CommonDto> allowanceGroups;
    private List<PayrollAllowanceLineDto> payrollAllowanceLines;

    private List<CommonDto> rewardGroups;
    private List<PayrollRewardLineDto> payrollRewardLines;
}
