package com.apus.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PayrollSearchCriteria {
    private String keyword;
    private Long departmentId;
    private Long positionId;
    private String cycle;
}
