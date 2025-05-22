package com.apus.demo.dto.request;

import com.apus.demo.dto.CommonSearchCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonSearchRequest {

    private String keyword;
    private Boolean isActive;

    private Integer page;
    private Integer size;
    private String sort;
    private String sortDirection;

    public CommonSearchCriteria toCommonSearchCriteria() {
        return CommonSearchCriteria.builder()
                .keyword(keyword)
                .isActive(isActive)
                .build();
    }
}
