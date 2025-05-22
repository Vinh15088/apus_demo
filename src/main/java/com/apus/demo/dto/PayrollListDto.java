package com.apus.demo.dto;

import com.apus.demo.dto.validator.EnumValue;
import com.apus.demo.util.enums.Cycle;
import com.apus.demo.util.enums.PayrollType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollListDto {

    private Long id;

    private CommonDto employee;
    private CommonDto department;
    private CommonDto position;

    @EnumValue(name = "payrollType", enumClass = PayrollType.class)
    private String payrollType;

    @EnumValue(name = "cycle", enumClass = Cycle.class)
    private String cycle;

    private BigDecimal totalAmount;
}
