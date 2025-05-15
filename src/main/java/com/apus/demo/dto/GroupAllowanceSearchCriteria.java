package com.apus.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupAllowanceSearchCriteria {
    private String keyword;
    private Boolean isActive;
} 