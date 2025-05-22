package com.apus.demo.repository.specification;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;



public class CommonSpecification {
    public static <T> Specification<T> hasCodeOrName(String keyword, String codeField, String nameField) {
        return (root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Path<String> code = root.get(codeField);
            Path<String> name = root.get(nameField);

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(code), "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(name), "%" + keyword.toLowerCase() + "%")
            );
        };
    }

    public static <T> Specification<T> hasIsActive(Boolean isActive, String fieldActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.conjunction();
            }

            Path<Boolean> isActivePath = root.get(fieldActive);

            return criteriaBuilder.equal(isActivePath, isActive);
        };
    }

    public static <T> Specification<T> hasStringField(String keyword, String field) {
        return (root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Path<String> code = root.get(field);

            return criteriaBuilder.like(criteriaBuilder.lower(code), "%" + keyword.toLowerCase() + "%");
        };
    }

}
