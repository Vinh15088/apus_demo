package com.apus.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RewardPolicySearchCriteria {
    private String keyword;
    private String applicableType;
    private String type;
}
