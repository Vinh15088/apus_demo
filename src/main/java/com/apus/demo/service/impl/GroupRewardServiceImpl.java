package com.apus.demo.service.impl;

import com.apus.demo.dto.GroupRewardDto;
import com.apus.demo.dto.GroupRewardSearchCriteria;
import com.apus.demo.entity.GroupRewardEntity;
import com.apus.demo.mapper.GroupRewardMapper;
import com.apus.demo.repository.GroupRewardRepository;
import com.apus.demo.repository.specification.GroupRewardSpecification;
import com.apus.demo.service.GroupRewardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "GROUP_REWARD_SERVICE")
@RequiredArgsConstructor
public class GroupRewardServiceImpl implements GroupRewardService {

    private final GroupRewardRepository groupRewardRepository;
    private final GroupRewardMapper groupRewardMapper;

    @Override
    @Transactional
    public Object addGroupReward(GroupRewardDto groupRewardDto) {
        log.info("Adding new group reward with code: {}", groupRewardDto.getCode());

        GroupRewardEntity groupReward = groupRewardMapper.toGroupReward(groupRewardDto);

        groupRewardRepository.save(groupReward);
        log.info("Successfully added group reward with id: {}", groupReward.getId());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", groupReward.getId());

        return node;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupRewardDto getGroupReward(Long id) {
        log.info("Fetching group reward with id: {}", id);

        GroupRewardEntity groupReward = groupRewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group reward not found with id: " + id));

        return groupRewardMapper.toGroupRewardDto(groupReward, groupRewardRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupRewardDto> searchGroupRewards(GroupRewardSearchCriteria criteria, Pageable pageable) {
        log.info("Searching group rewards with criteria: code={}, name={}, isActive={}",
                criteria.getKeyword(), criteria.getIsActive());

        Specification<GroupRewardEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(GroupRewardSpecification.hasCodeOrName(criteria.getKeyword()));
        }

        if (criteria.getIsActive() != null) {
            spec = spec.and(GroupRewardSpecification.hasIsActive(criteria.getIsActive()));
        }

        Page<GroupRewardEntity> groupRewardPage = groupRewardRepository.findAll(spec, pageable);

        return groupRewardPage.map(groupReward ->
                groupRewardMapper.toGroupRewardDto(groupReward, groupRewardRepository));
    }

    @Override
    @Transactional
    public Object updateGroupReward(GroupRewardDto groupRewardDto) {
        Long id = groupRewardDto.getId();

        log.info("Updating group reward with id: {}", id);

        GroupRewardEntity existingGroupReward = groupRewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group reward not found with id: " + id));

        GroupRewardEntity newGroupReward = groupRewardMapper.toGroupReward(groupRewardDto);

        // update fields
        existingGroupReward.setCode(newGroupReward.getCode());
        existingGroupReward.setName(newGroupReward.getName());
        existingGroupReward.setParentId(newGroupReward.getParentId());
        existingGroupReward.setDescription(newGroupReward.getDescription());
        existingGroupReward.setIsActive(newGroupReward.getIsActive());

        groupRewardRepository.save(existingGroupReward);
        log.info("Successfully updated group reward with id: {}", id);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", id);

        return node;
    }

    @Override
    @Transactional
    public void deleteGroupReward(Long id) {
        log.info("Deleting group reward with id: {}", id);

        if (!groupRewardRepository.existsById(id)) {
            throw new EntityNotFoundException("Group reward not found with id: " + id);
        }

        groupRewardRepository.deleteById(id);
        log.info("Successfully deleted group reward with id: {}", id);
    }
}
