package com.apus.demo.repository.specification;

import com.apus.demo.entity.GroupRewardEntity;
import com.apus.demo.entity.GroupRewardEntity_;
import org.springframework.data.jpa.domain.Specification;

public class GroupRewardSpecification {
    public static Specification<GroupRewardEntity> hasCodeOrName(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(GroupRewardEntity_.CODE)),
                            "%" + keyword.toLowerCase() + "%"
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(GroupRewardEntity_.NAME)),
                            "%" + keyword.toLowerCase() + "%"
                    )
            );
        };
    }

    public static Specification<GroupRewardEntity> hasIsActive(Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(GroupRewardEntity_.IS_ACTIVE), isActive);
        };
    }
}
