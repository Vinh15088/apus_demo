package com.apus.demo.dto;

import com.apus.demo.dto.validator.EnumValue;
import com.apus.demo.util.enums.AmountItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollRewardListLineDto{
    private Long id;

    @NotNull(message = "reward must be not null")
    private CommonDto reward;

    @NotNull(message = "amountItem must be not null")
    @EnumValue(name = "amountItem", enumClass = AmountItem.class)
    private String amountItem;

    @NotNull(message = "amount must be not null")
    private BigDecimal amount;

    @NotNull(message = "taxableAmount must be not null")
    private BigDecimal taxableAmount;

    @NotNull(message = "insuranceAmount must be not null")
    private BigDecimal insuranceAmount;
}
