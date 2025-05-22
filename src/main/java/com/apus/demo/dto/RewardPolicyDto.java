package com.apus.demo.dto;

import com.apus.demo.dto.validator.EnumValue;
import com.apus.demo.util.enums.ApplicableType;
import com.apus.demo.util.enums.PolicyType;
import com.apus.demo.util.enums.State;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RewardPolicyDto {
    private Long id;

    @NotBlank(message = "name must be not blank")
    private String name;

    @NotBlank(message = "code must be not blank")
    private String code;

    private String description;

    @EnumValue(name = "type", enumClass = PolicyType.class)
    @NotBlank(message = "type must be not blank")
    private String type;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @EnumValue(name = "applicableType", enumClass = ApplicableType.class)
    @NotBlank(message = "applicableType must be not blank")
    private String applicableType;

    @EnumValue(name = "state", enumClass = State.class)
    @NotBlank(message = "state must be not blank")
    private String state;

    private List<RewardPolicyLineDto> rewardPolicyLines;

    @NotNull(message = "rewardPolicyApplicableDto must be not null")
    private RewardPolicyApplicableDto rewardPolicyApplicable;
}
