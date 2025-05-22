package com.apus.demo.repository.specification;

import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.entity.RewardEntity;
import com.apus.demo.entity.RewardEntity_;
import org.springframework.data.jpa.domain.Specification;

public class RewardSpecification {

    public static Specification<RewardEntity> buildSpecification(CommonSearchCriteria criteria) {
        Specification<RewardEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(CommonSpecification.hasCodeOrName(criteria.getKeyword(),
                    RewardEntity_.CODE, RewardEntity_.NAME));
        }

        if (criteria.getIsActive() != null) {
            spec = spec.and(CommonSpecification.hasIsActive(criteria.getIsActive(), RewardEntity_.IS_ACTIVE));
        }

        return spec;
    }
}
