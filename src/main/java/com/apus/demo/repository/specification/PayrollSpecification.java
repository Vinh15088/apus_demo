package com.apus.demo.repository.specification;

import com.apus.demo.dto.PayrollSearchCriteria;
import com.apus.demo.entity.PayrollEntity;
import com.apus.demo.entity.PayrollEntity_;
import org.springframework.data.jpa.domain.Specification;

public class PayrollSpecification {

    public static Specification<PayrollEntity> buildSpecification(PayrollSearchCriteria criteria) {
        Specification<PayrollEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(CommonSpecification.hasCodeOrName(criteria.getKeyword(),
                    PayrollEntity_.EMPLOYEE_ID, PayrollEntity_.ID));
        }

        if (criteria.getDepartmentId() != null) {
            spec = spec.and(CommonSpecification.hasStringField(String.valueOf(criteria.getDepartmentId()),
                    PayrollEntity_.DEPARTMENT_ID));
        }

        if (criteria.getPositionId() != null) {
            spec = spec.and(CommonSpecification.hasStringField(String.valueOf(criteria.getPositionId()),
                    PayrollEntity_.POSITION_ID));
        }
//
//        if (criteria.getCycle() != null) {
//            spec = spec.and(CommonSpecification.hasStringField(String.valueOf(criteria.getCycle()),
//                    PayrollEntity_.CYCLE));
//        }

        return spec;
    }
}
