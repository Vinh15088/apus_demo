package com.apus.demo.service.impl;

import com.apus.demo.dto.*;
import com.apus.demo.entity.RewardPolicyEntity;
import com.apus.demo.mapper.RewardPolicyMapper;
import com.apus.demo.repository.RewardPolicyRepository;
import com.apus.demo.repository.specification.RewardPolicySpecification;
import com.apus.demo.service.RewardPolicyApplicableService;
import com.apus.demo.service.RewardPolicyLineService;
import com.apus.demo.service.RewardPolicyService;
import com.apus.demo.util.enums.ApplicableType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "REWARD_POLICY_SERVICE")
@RequiredArgsConstructor
public class RewardPolicyServiceImpl implements RewardPolicyService {

    private final RewardPolicyRepository rewardPolicyRepository;
    private final RewardPolicyMapper rewardPolicyMapper;
    private final RewardPolicyLineService rewardPolicyLineService;
    private final RewardPolicyApplicableService rewardPolicyApplicableService;

    @Override
    @Transactional
    public CommonDto createRewardPolicy(RewardPolicyDto rewardPolicyDto) {
        log.info("Adding new reward policy with code: {}", rewardPolicyDto.getCode());

        RewardPolicyEntity rewardPolicy = rewardPolicyMapper.toRewardPolicy(rewardPolicyDto);
        rewardPolicyRepository.save(rewardPolicy);

        ///  handle with applicable: with applicable type not equals all
        if(!rewardPolicyDto.getApplicableType().equals(String.valueOf(ApplicableType.All))) {
            rewardPolicyApplicableService.createRewardPolicyApplicable(rewardPolicy.getId(),
                    rewardPolicyDto.getRewardPolicyApplicable().getApplicableTarget().getId());
        }

        // handle with reward policy line
        rewardPolicyLineService.createOrUpdateRewardPolicyLines(rewardPolicy.getId(), rewardPolicyDto.getRewardPolicyLines());

        return CommonDto.builder().id(rewardPolicy.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public RewardPolicyDto getRewardPolicy(Long id) {
        log.info("Getting reward policy with id: {}", id);

        RewardPolicyDto rewardPolicyDto = rewardPolicyMapper.toRewardPolicyDto(getById(id));

        // handle with applicable
        rewardPolicyDto.setRewardPolicyApplicable(rewardPolicyApplicableService.getRewardPolicyApplicable(id,
                rewardPolicyDto.getApplicableType()));

        // handle with reward policy line
        rewardPolicyDto.setRewardPolicyLines(rewardPolicyLineService.getRewardPolicyLines(id));

        return rewardPolicyDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RewardPolicyListDto> getListRewardPolicy(RewardPolicySearchCriteria criteria, Pageable pageable) {
        log.info("Searching reward policy");

        Specification<RewardPolicyEntity> spec = RewardPolicySpecification.buildSpecification(criteria);

        Page<RewardPolicyEntity> rewardPolicyPage = rewardPolicyRepository.findAll(spec, pageable);

        return rewardPolicyPage.map(rewardPolicyMapper::toRewardPolicyListDto);
    }

    @Override
    @Transactional
    public CommonDto updateRewardPolicy(RewardPolicyDto rewardPolicyDto) {
        log.info("Updating reward policy with id: {}", rewardPolicyDto.getId());

        RewardPolicyEntity rewardPolicy = rewardPolicyMapper.toRewardPolicy(rewardPolicyDto);
        rewardPolicy.setId(rewardPolicyDto.getId());

        rewardPolicyRepository.save(rewardPolicy);

        // handle with applicable
        rewardPolicyApplicableService.updateRewardPolicyApplicable(rewardPolicy.getId(),  rewardPolicyDto.getApplicableType(),
                rewardPolicyDto.getRewardPolicyApplicable().getApplicableTarget().getId());

        // handle with reward policy line
        rewardPolicyLineService.createOrUpdateRewardPolicyLines(rewardPolicy.getId(), rewardPolicyDto.getRewardPolicyLines());

        return CommonDto.builder().id(rewardPolicy.getId()).build();
    }

    @Override
    @Transactional
    public void deleteRewardPolicy(Long id) {
        log.info("Deleting reward policy with id: {}", id);

        rewardPolicyRepository.delete(getById(id));
        log.info("Successfully deleted reward policy with id: {}", id);

        // handle with applicable
        rewardPolicyApplicableService.deleteRewardPolicyApplicable(id);

        // handle with reward policy line
        rewardPolicyLineService.deleteRewardPolicyLines(id);
    }

    private RewardPolicyEntity getById(Long id) {
        return rewardPolicyRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Reward policy not found with id: " + id));
    }

}
