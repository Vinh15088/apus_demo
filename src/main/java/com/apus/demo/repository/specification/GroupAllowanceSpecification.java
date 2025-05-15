package com.apus.demo.repository.specification;

import com.apus.demo.entity.GroupAllowanceEntity;
import com.apus.demo.entity.GroupAllowanceEntity_;
import org.springframework.data.jpa.domain.Specification;

public class GroupAllowanceSpecification {
    
    public static Specification<GroupAllowanceEntity> hasCodeOrName(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.or(
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(GroupAllowanceEntity_.CODE)),
                    "%" + keyword.toLowerCase() + "%"
                ),
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(GroupAllowanceEntity_.NAME)),
                    "%" + keyword.toLowerCase() + "%"
                )
            );
        };
    }

    public static Specification<GroupAllowanceEntity> hasIsActive(Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(GroupAllowanceEntity_.IS_ACTIVE), isActive);
        };
    }
} 