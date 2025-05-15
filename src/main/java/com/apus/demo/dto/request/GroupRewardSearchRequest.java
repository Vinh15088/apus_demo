package com.apus.demo.dto.request;

import com.apus.demo.dto.GroupRewardSearchCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRewardSearchRequest  {

    private String keyword;
    private Boolean isActive;

    private Integer page;
    private Integer size;
    private String sort;
    private String sortDirection;

    public GroupRewardSearchCriteria toGroupRewardSearchCriteria() {
        return GroupRewardSearchCriteria.builder()
                .keyword(keyword)
                .isActive(isActive)
                .build();
    }

}