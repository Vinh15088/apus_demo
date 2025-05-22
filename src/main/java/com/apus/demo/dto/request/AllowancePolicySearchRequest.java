package com.apus.demo.dto.request;

import com.apus.demo.dto.AllowancePolicySearchCriteria;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AllowancePolicySearchRequest {

    private String keyword;
    private String applicableType;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String state;

    private Integer page;
    private Integer size;
    private String sort;
    private String sortDirection;

    public AllowancePolicySearchCriteria toAllowancePolicySearchCriteria() {
        return AllowancePolicySearchCriteria.builder()
                .keyword(keyword)
                .applicableType(applicableType)
                .type(type)
                .startDate(startDate)
                .endDate(endDate)
                .state(state)
                .build();
    }
}
