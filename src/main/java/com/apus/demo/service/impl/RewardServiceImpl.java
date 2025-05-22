package com.apus.demo.service.impl;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.dto.RewardDto;
import com.apus.demo.dto.RewardListDto;
import com.apus.demo.entity.RewardEntity;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.mapper.RewardMapper;
import com.apus.demo.repository.RewardRepository;
import com.apus.demo.repository.specification.RewardSpecification;
import com.apus.demo.service.ProductManufactorServiceClient;
import com.apus.demo.service.ResourcesServiceClient;
import com.apus.demo.service.RewardService;
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
@Slf4j(topic = "REWARD_SERVICE")
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final RewardMapper rewardMapper;
    private final ResourcesServiceClient resourcesService;
    private final ProductManufactorServiceClient productManufactorService;

    @Override
    @Transactional
    public CommonDto createReward(RewardDto rewardDto) {
        log.info("Adding new reward with code: {}", rewardDto.getCode());

        RewardEntity reward = rewardMapper.toReward(rewardDto);

        rewardRepository.save(reward);
        log.info("Successfully added reward with id: {}", reward.getId());

        return CommonDto.builder().id(reward.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public RewardDto getReward(Long id) {
        log.info("Fetching reward with id: {}", id);

        RewardDto rewardDto = rewardMapper.toRewardDto(getById(id));

        // set uom and currency data from client to dto of reward
        rewardDto.setUom(resourcesService.getUom(rewardDto.getUom().getId()));
        rewardDto.setCurrency(productManufactorService.getCurrency(rewardDto.getCurrency().getId()));

        return rewardDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RewardListDto> getListRewards(CommonSearchCriteria criteria, Pageable pageable) {
        log.info("Searching rewards with criteria: keyword={}, isActive={}",
                criteria.getKeyword(), criteria.getIsActive());

        Specification<RewardEntity> spec = RewardSpecification.buildSpecification(criteria);

        Page<RewardEntity> rewardPage = rewardRepository.findAll(spec, pageable);

        return rewardPage.map(rewardMapper::toRewardListDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RewardDto> getMapRewardEntityByIds(Set<Long> ids) {
        log.info("Fetching rewards by ids: {}", ids);

        List<RewardEntity> entityList = rewardRepository.findAllById(ids);

        Map<Long, RewardEntity> rewardEntityMap = entityList.stream()
                .collect(Collectors.toMap(RewardEntity::getId, Function.identity()));
//        Map<Long, CommonDto> uomEntityMap = getMapUomEntityByIds(entityList);
//        Map<Long, CommonDto> currencyEntityMap = getMapCurrencyEntityByIds(entityList);

        return entityList.stream().map(reward -> {
            RewardDto dto = rewardMapper.toRewardDto(reward);

            if (dto.getGroupReward() != null && dto.getGroupReward().getId() != null) {
                RewardEntity rewardEntity = rewardEntityMap.get(dto.getGroupReward().getId());

                dto.setGroupReward(CommonDtoMapper.toCommonDto(rewardEntity));
            }
//            dto.setUom(uomEntityMap.get(dto.getId()));
//            dto.setCurrency(currencyEntityMap.get(dto.getId()));

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommonDto updateReward(RewardDto rewardDto) {
        Long id = rewardDto.getId();

        log.info("Updating reward with id: {}", id);

        RewardEntity newReward = rewardMapper.toReward(rewardDto);
        newReward.setId(id);
        rewardRepository.save(newReward);

        log.info("Successfully updated reward with id: {}", id);

        return CommonDto.builder().id(id).build();
    }

    @Override
    @Transactional
    public void deleteReward(Long id) {
        log.info("Deleting reward with id: {}", id);

        rewardRepository.delete(getById(id));
        log.info("Successfully deleted reward with id: {}", id);
    }

    private Map<Long, RewardEntity> getMapRewardEntityByIds(List<RewardEntity> entityList) {
        Set<Long> ids = entityList.stream().map(RewardEntity::getId).collect(Collectors.toSet());

        return rewardRepository.findAllById(ids).stream().collect(
                Collectors.toMap(RewardEntity::getId, Function.identity()));
    }

    private Map<Long, CommonDto> getMapUomEntityByIds(List<RewardEntity> entityList) {
        Set<Long> ids = entityList.stream().map(RewardEntity::getUomId).collect(Collectors.toSet());

        return resourcesService.getMapUomEntityByIds(ids);

    }

    private Map<Long, CommonDto> getMapCurrencyEntityByIds(List<RewardEntity> entityList) {
        Set<Long> ids = entityList.stream().map(RewardEntity::getCurrencyId).collect(Collectors.toSet());

        return productManufactorService.getMapCurrencyEntityByIds(ids);
    }

    private RewardEntity getById(Long id) {
        return rewardRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Reward not found with id: " + id));
    }
}
