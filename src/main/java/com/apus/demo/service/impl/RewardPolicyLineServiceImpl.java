package com.apus.demo.service.impl;

import com.apus.demo.dto.RewardPolicyLineDto;
import com.apus.demo.entity.RewardEntity;
import com.apus.demo.entity.RewardPolicyLineEntity;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.mapper.RewardPolicyLineMapper;
import com.apus.demo.repository.RewardPolicyLineRepository;
import com.apus.demo.repository.RewardRepository;
import com.apus.demo.service.RewardPolicyLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "REWARD_POLICY_LINE_SERVICE")
@RequiredArgsConstructor
public class RewardPolicyLineServiceImpl implements RewardPolicyLineService {

    private final RewardPolicyLineRepository rewardPolicyLineRepository;
    private final RewardPolicyLineMapper rewardPolicyLineMapper;
    private final RewardRepository rewardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RewardPolicyLineDto> getRewardPolicyLines(Long rewardPolicyId) {
        log.info("Getting reward policy lines with reward policy id: {}", rewardPolicyId);

        List<RewardPolicyLineEntity> rewardPolicyLines = rewardPolicyLineRepository.
                findAllByRewardPolicyId(rewardPolicyId);

        // get set ids of line rewards
        Set<Long> ids = rewardPolicyLines.stream().map(RewardPolicyLineEntity::getRewardId)
                .collect(Collectors.toSet());

        // get map of rewards from ids
        Map<Long, RewardEntity> rewardMap = getMapRewardEntityByIds(ids);

        return rewardPolicyLines.stream().map(line -> {
            RewardPolicyLineDto dto = rewardPolicyLineMapper.toRewardPolicyLineDto(line);

            if (rewardMap.containsKey(line.getRewardId())) {
                RewardEntity rewardEntity = rewardMap.get(line.getRewardId());

                dto.setReward(CommonDtoMapper.toCommonDto(rewardEntity));
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createOrUpdateRewardPolicyLines(Long rewardPolicyId, List<RewardPolicyLineDto> policyLinesDto) {
        log.info("Creating or updating reward policy lines with reward policy id: {}", rewardPolicyId);

        List<RewardPolicyLineEntity> existingLines = rewardPolicyLineRepository.findAllByRewardPolicyId(rewardPolicyId);

        Set<Long> newIds = policyLinesDto.stream().map(RewardPolicyLineDto::getId).collect(Collectors.toSet());

        // delete all lines that are not in the new list
        List<RewardPolicyLineEntity> deleteLines = existingLines.stream()
                .filter(line -> !newIds.contains(line.getId()))
                .toList();

        rewardPolicyLineRepository.deleteAll(deleteLines);

        // create or update all lines
        List<RewardPolicyLineEntity> rewardPolicyLineEntities = policyLinesDto.stream()
                .map(dto -> {
                    RewardPolicyLineEntity entity = rewardPolicyLineMapper.toRewardPolicyLine(dto);
                    entity.setRewardPolicyId(rewardPolicyId);

                    return entity;
                }).toList();

        rewardPolicyLineRepository.saveAll(rewardPolicyLineEntities);
    }

    @Override
    @Transactional
    public void deleteRewardPolicyLines(Long rewardPolicyId) {
        log.info("Deleting reward policy lines with reward policy id: {}", rewardPolicyId);

        rewardPolicyLineRepository.deleteAllByRewardPolicyId(rewardPolicyId);
    }

    private Map<Long, RewardEntity> getMapRewardEntityByIds(Set<Long> ids) {
        return rewardRepository.findAllById(ids).stream().collect(
                Collectors.toMap(RewardEntity::getId, Function.identity()));
    }
}
