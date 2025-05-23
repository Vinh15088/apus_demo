package com.apus.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollRewardLineDto {

    private Long id;

    private CommonDto rewardGroup;

    private List<PayrollRewardListLineDto> payrollRewardListLines;
}
