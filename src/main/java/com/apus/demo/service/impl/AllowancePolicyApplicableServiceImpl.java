package com.apus.demo.service.impl;

import com.apus.demo.dto.AllowancePolicyApplicableDto;
import com.apus.demo.dto.CommonDto;
import com.apus.demo.entity.AllowancePolicyApplicableEntity;
import com.apus.demo.mapper.AllowancePolicyApplicableMapper;
import com.apus.demo.repository.AllowancePolicyApplicableRepository;
import com.apus.demo.service.AllowancePolicyApplicableService;
import com.apus.demo.service.ProductManufactorServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "ALLOWANCE_POLICY_APPLICABLE_SERVICE")
@RequiredArgsConstructor
public class AllowancePolicyApplicableServiceImpl implements AllowancePolicyApplicableService {

    private final AllowancePolicyApplicableRepository allowancePolicyApplicableRepository;
    private final AllowancePolicyApplicableMapper allowancePolicyApplicableMapper;
    private final ProductManufactorServiceClient applicableClientService;

    @Override
    @Transactional(readOnly = true)
    public AllowancePolicyApplicableDto getAllowancePolicyApplicable(Long allowancePolicyId, String applicableType) {
        log.info("Retrieving applicable for allowance policy with id: {}", allowancePolicyId);

        AllowancePolicyApplicableEntity applicableEntity = getByAllowancePolicyId(allowancePolicyId);
        Long targetId = applicableEntity.getTargetId();

        CommonDto applicableTargetDto = switch (applicableType) {
            case "DEPARTMENT" -> applicableClientService.getDepartment(targetId);
            case "POSITION" -> applicableClientService.getPosition(targetId);
            case "EMPLOYEE" -> applicableClientService.getEmployee(targetId);
            default -> null;
        };

        return AllowancePolicyApplicableDto.builder()
                .id(applicableEntity.getId())
                .applicableTarget(applicableTargetDto)
                .build();
    }

    @Override
    @Transactional
    public void createAllowancePolicyApplicable(Long policyId, Long targetId) {
        log.info("Adding applicable for allowance policy with id: {}", policyId);

        AllowancePolicyApplicableEntity applicableEntity = AllowancePolicyApplicableEntity.builder()
                        .targetId(targetId)
                        .allowancePolicyId(policyId)
                        .build();

        allowancePolicyApplicableRepository.save(applicableEntity);
    }

    @Override
    public void updateAllowancePolicyApplicable(Long allowancePolicyId, String applicableType, Long applicableTargetId) {
        log.info("Updating applicable for allowance policy with id: {}", allowancePolicyId);

        AllowancePolicyApplicableEntity applicableEntity = getByAllowancePolicyId(allowancePolicyId);
        applicableEntity.setTargetId(applicableTargetId);

        allowancePolicyApplicableRepository.save(applicableEntity);
    }

    @Override
    public void deleteAllowancePolicyApplicable(Long allowancePolicyId) {
        log.info("Deleting applicable for allowance policy with id: {}", allowancePolicyId);

        allowancePolicyApplicableRepository.deleteByAllowancePolicyId(allowancePolicyId);
    }

    private AllowancePolicyApplicableEntity getByAllowancePolicyId(Long id) {
        return allowancePolicyApplicableRepository.findByAllowancePolicyId(id);
    }
}
