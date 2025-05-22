package com.apus.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AllowancePolicySearchCriteria {
    private String keyword;
    private String applicableType;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String state;
}
