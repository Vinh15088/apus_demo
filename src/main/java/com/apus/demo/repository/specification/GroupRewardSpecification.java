package com.apus.demo.repository.specification;

import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.entity.GroupRewardEntity;
import com.apus.demo.entity.GroupRewardEntity_;
import org.springframework.data.jpa.domain.Specification;

public class GroupRewardSpecification {

    public static Specification<GroupRewardEntity> buildSpecification(CommonSearchCriteria criteria) {
        Specification<GroupRewardEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(CommonSpecification.hasCodeOrName(criteria.getKeyword(),
                    GroupRewardEntity_.CODE, GroupRewardEntity_.NAME));
        }

        if (criteria.getIsActive() != null) {
            spec = spec.and(CommonSpecification.hasIsActive(criteria.getIsActive(), GroupRewardEntity_.IS_ACTIVE));
        }

        return spec;
    }
}
