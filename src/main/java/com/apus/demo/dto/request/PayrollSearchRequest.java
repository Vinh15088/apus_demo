package com.apus.demo.dto.request;

import com.apus.demo.dto.PayrollSearchCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollSearchRequest {

    private String keyword;
    private Long departmentId;
    private Long positionId;
    private String cycle;

    private Integer page;
    private Integer size;
    private String sort;
    private String sortDirection;

    public PayrollSearchCriteria toPayrollSearchCriteria() {
        return PayrollSearchCriteria.builder()
                .keyword(keyword)
                .departmentId(departmentId)
                .positionId(positionId)
                .cycle(cycle)
                .build();
    }
}
