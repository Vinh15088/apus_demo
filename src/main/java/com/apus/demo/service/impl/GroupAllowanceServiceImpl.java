package com.apus.demo.service.impl;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.entity.GroupAllowanceEntity;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.mapper.GroupAllowanceMapper;
import com.apus.demo.repository.GroupAllowanceRepository;
import com.apus.demo.service.GroupAllowanceService;
import com.apus.demo.repository.specification.GroupAllowanceSpecification;
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
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "GROUP_ALLOWANCE_SERVICE")
@RequiredArgsConstructor
public class GroupAllowanceServiceImpl implements GroupAllowanceService {

    private final GroupAllowanceRepository groupAllowanceRepository;
    private final GroupAllowanceMapper groupAllowanceMapper;

    @Override
    @Transactional
    public CommonDto createGroupAllowance(GroupAllowanceDto groupAllowanceDto) {
        log.info("Adding new group allowance with code: {}", groupAllowanceDto.getCode());

        GroupAllowanceEntity groupAllowance = groupAllowanceMapper.toGroupAllowance(groupAllowanceDto);

        groupAllowanceRepository.save(groupAllowance);
        log.info("Successfully added group allowance with id: {}", groupAllowance.getId());

        return CommonDto.builder().id(groupAllowance.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public GroupAllowanceDto getGroupAllowance(Long id) {
        log.info("Fetching group allowance with id: {}", id);

        GroupAllowanceDto groupAllowanceDto = groupAllowanceMapper.toGroupAllowanceDto(getById(id));

        GroupAllowanceEntity parent = getById(groupAllowanceDto.getId());

        groupAllowanceDto.setParent(CommonDtoMapper.toCommonDto(parent));

        return groupAllowanceDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupAllowanceDto> getListGroupAllowances(CommonSearchCriteria criteria, Pageable pageable) {
        log.info("Searching group allowances with criteria: keyword={}, isActive={}",
            criteria.getKeyword(), criteria.getIsActive());

        Specification<GroupAllowanceEntity> spec = GroupAllowanceSpecification.buildSpecification(criteria);

        Page<GroupAllowanceEntity> groupAllowancePage = groupAllowanceRepository.findAll(spec, pageable);

        Set<Long> ids = groupAllowancePage.getContent().stream()
                .map(GroupAllowanceEntity::getParentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, GroupAllowanceEntity> groupRewardEntityMap = getMapGroupAllowanceEntityByIds(ids);

        return groupAllowancePage.map(groupAllowance -> {
            GroupAllowanceDto dto = groupAllowanceMapper.toGroupAllowanceDto(groupAllowance);

            if (dto.getParent() != null && dto.getParent().getId() != null) {
                GroupAllowanceEntity groupAllowanceEntity = groupRewardEntityMap.get(dto.getParent().getId());

                if(Objects.nonNull(groupAllowanceEntity)){
                    dto.setParent(CommonDtoMapper.toCommonDto(groupAllowanceEntity));
                }
            }

            return dto;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupAllowanceDto> getGroupAllowancesByIds(Set<Long> ids) {
        log.info("Fetching group allowances with ids: {}", ids);

        return groupAllowanceRepository.findAllById(ids).stream()
                .map(groupAllowanceMapper::toGroupAllowanceDto)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public CommonDto updateGroupAllowance(GroupAllowanceDto groupAllowanceDto) {
        Long id = groupAllowanceDto.getId();

        log.info("Updating group allowance with id: {}", id);

        GroupAllowanceEntity newGroupAllowance = groupAllowanceMapper.toGroupAllowance(groupAllowanceDto);
        newGroupAllowance.setId(id);
        
        groupAllowanceRepository.save(newGroupAllowance);
        log.info("Successfully updated group allowance with id: {}", id);

        return CommonDto.builder().id(id).build();
    }

    @Override
    @Transactional
    public void deleteGroupAllowance(Long id) {
        log.info("Deleting group allowance with id: {}", id);
        
        groupAllowanceRepository.delete(getById(id));

        log.info("Successfully deleted group allowance with id: {}", id);
    }

    private Map<Long, GroupAllowanceEntity> getMapGroupAllowanceEntityByIds(Set<Long> ids) {
        return groupAllowanceRepository.findAllById(ids).stream().collect(
                Collectors.toMap(GroupAllowanceEntity::getId, Function.identity()));
    }

    private GroupAllowanceEntity getById(Long id){
        return groupAllowanceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Group allowance not found with id: " + id));
    }
}
