package com.apus.demo.dto.request;

import com.apus.demo.dto.RewardPolicySearchCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardPolicySearchRequest {
    private String keyword;
    private String applicableType;
    private String type;

    private Integer page;
    private Integer size;
    private String sort;
    private String sortDirection;

    public RewardPolicySearchCriteria toRewardPolicySearchCriteria() {
        return RewardPolicySearchCriteria.builder()
                .keyword(keyword)
                .applicableType(applicableType)
                .type(type)
                .build();
    }
}
