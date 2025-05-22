package com.apus.demo.service.impl;

import com.apus.demo.dto.*;
import com.apus.demo.entity.AllowancePolicyEntity;
import com.apus.demo.mapper.AllowancePolicyMapper;
import com.apus.demo.repository.AllowancePolicyRepository;
import com.apus.demo.repository.specification.AllowancePolicySpecification;
import com.apus.demo.service.AllowancePolicyApplicableService;
import com.apus.demo.service.AllowancePolicyLineService;
import com.apus.demo.service.AllowancePolicyService;
import com.apus.demo.util.enums.ApplicableType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j(topic = "ALLOWANCE_POLICY_SERVICE")
@RequiredArgsConstructor
public class AllowancePolicyServiceImpl implements AllowancePolicyService {

    private final AllowancePolicyRepository allowancePolicyRepository;
    private final AllowancePolicyMapper allowancePolicyMapper;
    private final AllowancePolicyLineService allowancePolicyLineService;
    private final AllowancePolicyApplicableService allowancePolicyApplicableService;

    @Override
    @Transactional
    public CommonDto createAllowancePolicy(AllowancePolicyDto allowancePolicyDto) {
        log.info("Adding new allowance policy with code: {}", allowancePolicyDto.getCode());

        AllowancePolicyEntity policyEntity = allowancePolicyMapper.toAllowancePolicy(allowancePolicyDto);
        allowancePolicyRepository.save(policyEntity);

        // handle with applicable
        if(!allowancePolicyDto.getApplicableType().equals(String.valueOf(ApplicableType.All))) {
            allowancePolicyApplicableService.createAllowancePolicyApplicable(policyEntity.getId(),
                    allowancePolicyDto.getAllowancePolicyApplicable().getApplicableTarget().getId());
        }

        // handle with allowance policy line
        allowancePolicyLineService.createOrUpdateAllowancePolicyLines(policyEntity.getId(),
                allowancePolicyDto.getAllowancePolicyLines());

        return CommonDto.builder().id(policyEntity.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public AllowancePolicyDto getAllowancePolicy(Long id) {
        log.info("Getting allowance policy with id: {}", id);

        AllowancePolicyDto allowancePolicyDto = allowancePolicyMapper.toAllowancePolicyDto(getById(id));

        // handle with applicable
        allowancePolicyDto.setAllowancePolicyApplicable(allowancePolicyApplicableService
                .getAllowancePolicyApplicable(id, allowancePolicyDto.getApplicableType()));

        // handle with allowance policy line
        allowancePolicyDto.setAllowancePolicyLines(allowancePolicyLineService.getAllowancePolicyLines(id));

        return allowancePolicyDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllowancePolicyListDto> getListAllowancePolicy(AllowancePolicySearchCriteria criteria, Pageable pageable) {
        log.info("Searching allowance policy");

        Specification<AllowancePolicyEntity> spec = AllowancePolicySpecification.buildSpecification(criteria);

        Page<AllowancePolicyEntity> page = allowancePolicyRepository.findAll(spec, pageable);

        return page.map(allowancePolicyMapper::toAllowancePolicyListDto);
    }


    @Override
    @Transactional
    public CommonDto updateAllowancePolicy(AllowancePolicyDto allowancePolicyDto) {
        log.info("Updating allowance policy with id: {}", allowancePolicyDto.getId());

        AllowancePolicyEntity policyEntity = allowancePolicyMapper.toAllowancePolicy(allowancePolicyDto);
        policyEntity.setId(allowancePolicyDto.getId());

        allowancePolicyRepository.save(policyEntity);

        // handle with applicable
        allowancePolicyApplicableService.updateAllowancePolicyApplicable(policyEntity.getId(),
                allowancePolicyDto.getApplicableType(),
                allowancePolicyDto.getAllowancePolicyApplicable().getApplicableTarget().getId());

        // handle with allowance policy line
        allowancePolicyLineService.createOrUpdateAllowancePolicyLines(policyEntity.getId(),
                allowancePolicyDto.getAllowancePolicyLines());

        return CommonDto.builder().id(policyEntity.getId()).build();
    }

    @Override
    @Transactional
    public void deleteAllowancePolicy(Long id) {
        log.info("Deleting allowance policy with id: {}", id);

        allowancePolicyRepository.delete(getById(id));
        log.info("Successfully deleted allowance policy with id: {}", id);

        // handle with applicable
        allowancePolicyApplicableService.deleteAllowancePolicyApplicable(id);

        // handle with allowance policy line
        allowancePolicyLineService.deleteAllowancePolicyLines(id);

    }

    private AllowancePolicyEntity getById(Long id) {
        return allowancePolicyRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Allowance policy not found with id: " + id));
    }
}
