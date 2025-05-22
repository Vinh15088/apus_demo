package com.apus.demo.service.impl;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.dto.GroupRewardDto;
import com.apus.demo.entity.GroupRewardEntity;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.mapper.GroupRewardMapper;
import com.apus.demo.repository.GroupRewardRepository;
import com.apus.demo.repository.specification.GroupRewardSpecification;
import com.apus.demo.service.GroupRewardService;
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
@Slf4j(topic = "GROUP_REWARD_SERVICE")
@RequiredArgsConstructor
public class GroupRewardServiceImpl implements GroupRewardService {

    private final GroupRewardRepository groupRewardRepository;
    private final GroupRewardMapper groupRewardMapper;

    @Override
    @Transactional
    public CommonDto createGroupReward(GroupRewardDto groupRewardDto) {
        log.info("Adding new group reward with code: {}", groupRewardDto.getCode());

        GroupRewardEntity groupReward = groupRewardMapper.toGroupReward(groupRewardDto);

        groupRewardRepository.save(groupReward);
        log.info("Successfully added group reward with id: {}", groupReward.getId());

        return CommonDto.builder().id(groupReward.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public GroupRewardDto getGroupReward(Long id) {
        log.info("Fetching group reward with id: {}", id);

        GroupRewardDto groupRewardDto = groupRewardMapper.toGroupRewardDto(getById(id));

        GroupRewardEntity parent = getById(groupRewardDto.getId());

        groupRewardDto.setParent(CommonDtoMapper.toCommonDto(parent));

        return groupRewardDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupRewardDto> getListGroupRewards(CommonSearchCriteria criteria, Pageable pageable) {
        log.info("Searching group rewards with criteria: keyword={}, isActive={}",
                criteria.getKeyword(), criteria.getIsActive());

        Specification<GroupRewardEntity> spec = GroupRewardSpecification.buildSpecification(criteria);

        Page<GroupRewardEntity> groupRewardPage = groupRewardRepository.findAll(spec, pageable);

        // get set id group reward of parent
        Set<Long> ids = groupRewardPage.getContent().stream()
                .map(GroupRewardEntity::getParentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // get group rewards by set ids
        Map<Long, GroupRewardEntity> groupRewardEntityMap = getMapGroupAllowanceEntityByIds(ids);

        return groupRewardPage.map(groupReward -> {
            GroupRewardDto dto = groupRewardMapper.toGroupRewardDto(groupReward);

            if (dto.getParent() != null && dto.getParent().getId() != null) {
                GroupRewardEntity groupRewardEntity = groupRewardEntityMap.get(dto.getParent().getId());

                if(Objects.nonNull(groupRewardEntity)){
                    dto.setParent(CommonDtoMapper.toCommonDto(groupRewardEntity));
                }
            }

            return dto;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupRewardDto> getGroupAllowanceEntityByIds(Set<Long> ids) {
        log.info("Fetching group rewards by ids: {}", ids);

        return groupRewardRepository.findAllById(ids).stream()
                .map(groupRewardMapper::toGroupRewardDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommonDto updateGroupReward(GroupRewardDto groupRewardDto) {
        Long id = groupRewardDto.getId();

        log.info("Updating group reward with id: {}", id);

        GroupRewardEntity newGroupReward = groupRewardMapper.toGroupReward(groupRewardDto);
        newGroupReward.setId(id);

        groupRewardRepository.save(newGroupReward);
        log.info("Successfully updated group reward with id: {}", id);

        return CommonDto.builder().id(id).build();
    }

    @Override
    @Transactional
    public void deleteGroupReward(Long id) {
        log.info("Deleting group reward with id: {}", id);

        groupRewardRepository.delete(getById(id));
        log.info("Successfully deleted group reward with id: {}", id);
    }

    private Map<Long, GroupRewardEntity> getMapGroupAllowanceEntityByIds(Set<Long> ids) {
        return groupRewardRepository.findAllById(ids).stream().collect(
                Collectors.toMap(GroupRewardEntity::getId, Function.identity()));
    }

    private GroupRewardEntity getById(Long id) {
        return groupRewardRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Group reward not found with id: " + id));
    }
}
