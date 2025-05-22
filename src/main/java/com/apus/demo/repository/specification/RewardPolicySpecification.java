package com.apus.demo.repository.specification;

import com.apus.demo.dto.RewardPolicySearchCriteria;
import com.apus.demo.entity.AllowancePolicyEntity_;
import com.apus.demo.entity.RewardPolicyEntity;
import com.apus.demo.entity.RewardPolicyEntity_;
import org.springframework.data.jpa.domain.Specification;

public class RewardPolicySpecification {
    public static Specification<RewardPolicyEntity> buildSpecification(RewardPolicySearchCriteria criteria) {
        Specification<RewardPolicyEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(CommonSpecification.hasCodeOrName(criteria.getKeyword(),
                    RewardPolicyEntity_.CODE, AllowancePolicyEntity_.NAME));
        }

        if (criteria.getApplicableType() != null) {
            spec = spec.and(CommonSpecification.hasStringField(criteria.getApplicableType(),
                    RewardPolicyEntity_.APPLICABLE_TYPE));
        }

        if (criteria.getType() != null) {
            spec = spec.and(CommonSpecification.hasStringField(criteria.getType(),
                    RewardPolicyEntity_.TYPE));
        }

        return spec;
    }
}
