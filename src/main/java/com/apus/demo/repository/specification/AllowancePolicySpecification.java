package com.apus.demo.repository.specification;

import com.apus.demo.dto.AllowancePolicySearchCriteria;
import com.apus.demo.entity.AllowancePolicyEntity;
import com.apus.demo.entity.AllowancePolicyEntity_;
import org.springframework.data.jpa.domain.Specification;

public class AllowancePolicySpecification {

    public static Specification<AllowancePolicyEntity> buildSpecification(AllowancePolicySearchCriteria criteria) {
        Specification<AllowancePolicyEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(CommonSpecification.hasCodeOrName(criteria.getKeyword(),
                    AllowancePolicyEntity_.CODE, AllowancePolicyEntity_.NAME));
        }

        if(criteria.getApplicableType() != null) {
            spec = spec.and(CommonSpecification.hasStringField(criteria.getApplicableType(),
                    AllowancePolicyEntity_.APPLICABLE_TYPE));
        }

        if(criteria.getType() != null) {
            spec = spec.and(CommonSpecification.hasStringField(criteria.getType(),
                    AllowancePolicyEntity_.TYPE));
        }

        if(criteria.getStartDate() != null) {
            spec = spec.and(CommonSpecification.hasStringField(String.valueOf(criteria.getStartDate()),
                    AllowancePolicyEntity_.START_DATE));
        };

        if(criteria.getEndDate() != null) {
            spec = spec.and(CommonSpecification.hasStringField(String.valueOf(criteria.getEndDate()),
                    AllowancePolicyEntity_.END_DATE));
        };

        if(criteria.getState() != null) {
            spec = spec.and(CommonSpecification.hasStringField(criteria.getState(),
                    AllowancePolicyEntity_.STATE));
        }

        return spec;
    }
}
