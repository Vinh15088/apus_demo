package com.apus.demo.service.impl;

import com.apus.demo.dto.AllowanceDto;
import com.apus.demo.dto.AllowanceListDto;
import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.entity.AllowanceEntity;
import com.apus.demo.mapper.AllowanceMapper;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.repository.AllowanceRepository;
import com.apus.demo.repository.specification.AllowanceSpecification;
import com.apus.demo.service.AllowanceService;
import com.apus.demo.service.ProductManufactorServiceClient;
import com.apus.demo.service.ResourcesServiceClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "ALLOWANCE_SERVICE")
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final AllowanceMapper allowanceMapper;
    private final ResourcesServiceClient resourcesService;
    private final ProductManufactorServiceClient productManufactorService;

    @Override
    @Transactional
    public CommonDto createAllowance(AllowanceDto allowanceDto) {

        log.info("Adding new allowance with code: {}", allowanceDto.getCode());

        AllowanceEntity allowance = allowanceMapper.toAllowance(allowanceDto);
        allowanceRepository.save(allowance);

        log.info("Successfully added allowance with id: {}", allowance.getId());

        return CommonDto.builder().id(allowance.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public AllowanceDto getAllowance(Long id) {
        log.info("Fetching allowance with id: {}", id);

        AllowanceDto allowanceDto = allowanceMapper.toAllowanceDto(getById(id));

        // set uom and currency data from client to dto of allowance
        allowanceDto.setUom(resourcesService.getUom(allowanceDto.getUom().getId()));
        allowanceDto.setCurrency(productManufactorService.getCurrency(allowanceDto.getCurrency().getId()));

        return allowanceDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllowanceListDto> getListAllowances(CommonSearchCriteria criteria, Pageable pageable) {
        log.info("Searching allowances with criteria: keyword={}, isActive={}",
                criteria.getKeyword(), criteria.getIsActive());

        Specification<AllowanceEntity> spec = AllowanceSpecification.buildSpecification(criteria);

        Page<AllowanceEntity> allowancePage = allowanceRepository.findAll(spec, pageable);

        Map<Long, AllowanceEntity> allowanceEntityMap = getMapAllowanceEntityByIds(allowancePage.getContent());

        return allowancePage.map(allowance -> {
            AllowanceListDto dto = allowanceMapper.toAllowanceListDto(allowance);

            if (dto.getGroupAllowance() != null && dto.getGroupAllowance().getId() != null) {
                AllowanceEntity allowanceEntity = allowanceEntityMap.get(dto.getGroupAllowance().getId());

                dto.setGroupAllowance(CommonDtoMapper.toCommonDto(allowanceEntity));
            }
            return dto;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllowanceDto> getMapAllowanceEntityByIds(Set<Long> ids) {
        log.info("Fetching allowances by ids: {}", ids);

        List<AllowanceEntity> entityList = allowanceRepository.findAllById(ids);

        Map<Long, AllowanceEntity> allowanceEntityMap = entityList.stream()
                .collect(Collectors.toMap(AllowanceEntity::getId, Function.identity()));
//        Map<Long, CommonDto> uomEntityMap = getMapUomEntityByIds(entityList);
//        Map<Long, CommonDto> currencyEntityMap = getMapCurrencyEntityByIds(entityList);

        return entityList.stream().map(allowance -> {
            AllowanceDto dto = allowanceMapper.toAllowanceDto(allowance);

            if (dto.getGroupAllowance() != null && dto.getGroupAllowance().getId() != null) {
                AllowanceEntity allowanceEntity = allowanceEntityMap.get(dto.getGroupAllowance().getId());

                dto.setGroupAllowance(CommonDtoMapper.toCommonDto(allowanceEntity));

                log.info("id: {}", dto.getGroupAllowance().getId());
                log.info("name: {}", dto.getGroupAllowance().getName());
                log.info("code: {}", dto.getGroupAllowance().getCode());
            }
//            dto.setUom(uomEntityMap.get(dto.getId()));
//            dto.setCurrency(currencyEntityMap.get(dto.getId()));

            return dto;
        }).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public CommonDto updateAllowance(AllowanceDto allowanceDto) {
        Long id = allowanceDto.getId();

        log.info("Updating allowance with id: {}", id);

        AllowanceEntity newAllowance = allowanceMapper.toAllowance(allowanceDto);
        newAllowance.setId(id);
        allowanceRepository.save(newAllowance);

        log.info("Successfully updated allowance with id: {}", id);

        return CommonDto.builder().id(id).build();
    }

    @Override
    @Transactional
    public void deleteAllowance(Long id) {
        log.info("Deleting allowance with id: {}", id);

        allowanceRepository.delete(getById(id));

        log.info("Successfully deleted allowance with id: {}", id);
    }

    private Map<Long, AllowanceEntity> getMapAllowanceEntityByIds(List<AllowanceEntity> entityList) {
        Set<Long> ids = entityList.stream().map(AllowanceEntity::getId).collect(Collectors.toSet());

        return allowanceRepository.findAllById(ids).stream().collect(
                Collectors.toMap(AllowanceEntity::getId, Function.identity()));
    }

    private Map<Long, CommonDto> getMapUomEntityByIds(List<AllowanceEntity> entityList) {
        Set<Long> ids = entityList.stream().map(AllowanceEntity::getUomId).collect(Collectors.toSet());

        return resourcesService.getMapUomEntityByIds(ids);

    }

    private Map<Long, CommonDto> getMapCurrencyEntityByIds(List<AllowanceEntity> entityList) {
        Set<Long> ids = entityList.stream().map(AllowanceEntity::getCurrencyId).collect(Collectors.toSet());

        return productManufactorService.getMapCurrencyEntityByIds(ids);
    }

    private AllowanceEntity getById(Long id) {
        return allowanceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Allowance not found with id: " + id));
    }

}
