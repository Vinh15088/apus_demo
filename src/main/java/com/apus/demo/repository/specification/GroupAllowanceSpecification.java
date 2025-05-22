package com.apus.demo.repository.specification;

import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.entity.GroupAllowanceEntity;
import com.apus.demo.entity.GroupAllowanceEntity_;
import org.springframework.data.jpa.domain.Specification;

public class GroupAllowanceSpecification {

    public static Specification<GroupAllowanceEntity> buildSpecification(CommonSearchCriteria criteria) {
        Specification<GroupAllowanceEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(CommonSpecification.hasCodeOrName(criteria.getKeyword(),
                    GroupAllowanceEntity_.CODE, GroupAllowanceEntity_.NAME));
        }

        if (criteria.getIsActive() != null) {
            spec = spec.and(CommonSpecification.hasIsActive(criteria.getIsActive(), GroupAllowanceEntity_.IS_ACTIVE));
        }

        return spec;
    }
} 