package com.apus.demo.service.impl;

import com.apus.demo.dto.AllowancePolicyLineDto;
import com.apus.demo.entity.AllowanceEntity;
import com.apus.demo.entity.AllowancePolicyLineEntity;
import com.apus.demo.mapper.AllowancePolicyLineMapper;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.repository.AllowancePolicyLineRepository;
import com.apus.demo.repository.AllowanceRepository;
import com.apus.demo.service.AllowancePolicyLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "ALLOWANCE_POLICY_LINE_SERVICE")
@RequiredArgsConstructor
public class AllowancePolicyLineServiceImpl implements AllowancePolicyLineService {

    private final AllowancePolicyLineRepository allowancePolicyLineRepository;
    private final AllowancePolicyLineMapper allowancePolicyLineMapper;
    private final AllowanceRepository allowanceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AllowancePolicyLineDto> getAllowancePolicyLines(Long allowancePolicyId) {
        log.info("Getting allowance policy lines with allowance policy id: {}", allowancePolicyId);

        List<AllowancePolicyLineEntity> allowancePolicyLines = allowancePolicyLineRepository.
                findAllByAllowancePolicyId(allowancePolicyId);

        // get set ids of line allowances
        Set<Long> ids = allowancePolicyLines.stream().map(AllowancePolicyLineEntity::getAllowanceId)
                .collect(Collectors.toSet());

        // get map of allowances from ids
        Map<Long, AllowanceEntity> allowanceMap = getMapAllowanceEntityByIds(ids);

        return allowancePolicyLines.stream().map(line -> {
            AllowancePolicyLineDto dto = allowancePolicyLineMapper.toAllowancePolicyLineDto(line);

            if (allowanceMap.containsKey(line.getAllowanceId())) {
                AllowanceEntity allowanceEntity = allowanceMap.get(line.getAllowanceId());

                dto.setAllowance(CommonDtoMapper.toCommonDto(allowanceEntity));
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createOrUpdateAllowancePolicyLines(Long allowancePolicyId, List<AllowancePolicyLineDto> policyLinesDto) {
        log.info("Creating or updating allowance policy lines with allowance policy id: {}", allowancePolicyId);

        List<AllowancePolicyLineEntity> existingLines = allowancePolicyLineRepository.findAllByAllowancePolicyId(allowancePolicyId);

        Set<Long> newIds = policyLinesDto.stream().map(AllowancePolicyLineDto::getId).collect(Collectors.toSet());

        // delete all lines that are not in the new list
        List<AllowancePolicyLineEntity> deleteLines = existingLines.stream()
                .filter(line -> !newIds.contains(line.getId()))
                .toList();

        allowancePolicyLineRepository.deleteAll(deleteLines);

        // create or update all lines
        List<AllowancePolicyLineEntity> allowancePolicyLineEntities = policyLinesDto.stream()
                .map(dto -> {
                    AllowancePolicyLineEntity entity = allowancePolicyLineMapper.toAllowancePolicyLine(dto);
                    entity.setAllowancePolicyId(allowancePolicyId);

                    return entity;
                }).toList();

        allowancePolicyLineRepository.saveAll(allowancePolicyLineEntities);
    }

    @Override
    @Transactional
    public void deleteAllowancePolicyLines(Long allowancePolicyId) {
        log.info("Deleting all allowance policy lines with allowance policy id: {}", allowancePolicyId);

        allowancePolicyLineRepository.deleteAllByAllowancePolicyId(allowancePolicyId);
    }

    private Map<Long, AllowanceEntity> getMapAllowanceEntityByIds(Set<Long> ids) {
        return allowanceRepository.findAllById(ids).stream().collect(
                Collectors.toMap(AllowanceEntity::getId, Function.identity()));
    }
}
