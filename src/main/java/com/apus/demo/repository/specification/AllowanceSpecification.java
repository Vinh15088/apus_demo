package com.apus.demo.repository.specification;

import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.entity.AllowanceEntity;
import com.apus.demo.entity.AllowanceEntity_;
import org.springframework.data.jpa.domain.Specification;

public class AllowanceSpecification {

    public static Specification<AllowanceEntity> buildSpecification(CommonSearchCriteria criteria) {
        Specification<AllowanceEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(CommonSpecification.hasCodeOrName(criteria.getKeyword(),
                    AllowanceEntity_.CODE, AllowanceEntity_.NAME));
        }

        if (criteria.getIsActive() != null) {
            spec = spec.and(CommonSpecification.hasIsActive(criteria.getIsActive(), AllowanceEntity_.IS_ACTIVE));
        }

        return spec;
    }

}
