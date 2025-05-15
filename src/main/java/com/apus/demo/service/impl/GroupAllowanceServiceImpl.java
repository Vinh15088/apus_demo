package com.apus.demo.service.impl;

import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.dto.GroupAllowanceSearchCriteria;
import com.apus.demo.entity.GroupAllowanceEntity;
import com.apus.demo.mapper.GroupAllowanceMapper;
import com.apus.demo.repository.GroupAllowanceRepository;
import com.apus.demo.service.GroupAllowanceService;
import com.apus.demo.repository.specification.GroupAllowanceSpecification;
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
@Slf4j(topic = "GROUP_ALLOWANCE_SERVICE")
@RequiredArgsConstructor
public class GroupAllowanceServiceImpl implements GroupAllowanceService {

    private final GroupAllowanceRepository groupAllowanceRepository;
    private final GroupAllowanceMapper groupAllowanceMapper;

    @Override
    @Transactional
    public Object addGroupAllowance(GroupAllowanceDto groupAllowanceDto) {
        log.info("Adding new group allowance with code: {}", groupAllowanceDto.getCode());

        GroupAllowanceEntity groupAllowance = groupAllowanceMapper.toGroupAllowance(groupAllowanceDto);

        groupAllowanceRepository.save(groupAllowance);
        log.info("Successfully added group allowance with id: {}", groupAllowance.getId());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", groupAllowance.getId());

        return node;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupAllowanceDto getGroupAllowance(Long id) {
        log.info("Fetching group allowance with id: {}", id);

        GroupAllowanceEntity groupAllowance = groupAllowanceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Group allowance not found with id: " + id));

        return groupAllowanceMapper.toGroupAllowanceDto(groupAllowance, groupAllowanceRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupAllowanceDto> searchGroupAllowances(GroupAllowanceSearchCriteria criteria, Pageable pageable) {
        log.info("Searching group allowances with criteria: code={}, name={}, isActive={}",
            criteria.getKeyword(), criteria.getIsActive());

        Specification<GroupAllowanceEntity> spec = Specification.where(null);

        if (criteria.getKeyword() != null) {
            spec = spec.and(GroupAllowanceSpecification.hasCodeOrName(criteria.getKeyword()));
        }

        if (criteria.getIsActive() != null) {
            spec = spec.and(GroupAllowanceSpecification.hasIsActive(criteria.getIsActive()));
        }

        Page<GroupAllowanceEntity> groupAllowancePage = groupAllowanceRepository.findAll(spec, pageable);

        return groupAllowancePage.map(groupAllowance ->
                groupAllowanceMapper.toGroupAllowanceDto(groupAllowance, groupAllowanceRepository));
    }

    @Override
    @Transactional
    public Object updateGroupAllowance(GroupAllowanceDto groupAllowanceDto) {
        Long id = groupAllowanceDto.getId();

        log.info("Updating group allowance with id: {}", id);

        GroupAllowanceEntity existingGroupAllowance = groupAllowanceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Group allowance not found with id: " + id));

        GroupAllowanceEntity newGroupAllowance = groupAllowanceMapper.toGroupAllowance(groupAllowanceDto);

        // update fields
        existingGroupAllowance.setCode(newGroupAllowance.getCode());
        existingGroupAllowance.setName(newGroupAllowance.getName());
        existingGroupAllowance.setParentId(newGroupAllowance.getParentId());
        existingGroupAllowance.setDescription(newGroupAllowance.getDescription());
        existingGroupAllowance.setIsActive(newGroupAllowance.getIsActive());
        
        groupAllowanceRepository.save(existingGroupAllowance);
        log.info("Successfully updated group allowance with id: {}", id);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", id);

        return node;
    }

    @Override
    @Transactional
    public void deleteGroupAllowance(Long id) {
        log.info("Deleting group allowance with id: {}", id);
        
        if (!groupAllowanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Group allowance not found with id: " + id);
        }
        
        groupAllowanceRepository.deleteById(id);
        log.info("Successfully deleted group allowance with id: {}", id);
    }

}
