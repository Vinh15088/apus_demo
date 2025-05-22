package com.apus.demo.dto;

import com.apus.demo.dto.validator.EnumValue;
import com.apus.demo.util.enums.Cycle;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowancePolicyLineDto {

    private Long id;

    @NotNull(message = "allowance must be not null")
    private CommonDto allowance;

    @EnumValue(name = "cycle", enumClass = Cycle.class)
    @NotBlank(message = "cycle must be not blank")
    private String cycle;

    @NotNull(message = "amount must be not null")
    private BigDecimal amount;
}
