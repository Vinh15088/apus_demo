package com.apus.demo.service.impl;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.RewardPolicyApplicableDto;
import com.apus.demo.entity.RewardPolicyApplicableEntity;
import com.apus.demo.repository.RewardPolicyApplicableRepository;
import com.apus.demo.service.ProductManufactorServiceClient;
import com.apus.demo.service.RewardPolicyApplicableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "REWARD_POLICY_APPLICABLE_SERVICE")
@RequiredArgsConstructor
public class RewardPolicyApplicableServiceImpl implements RewardPolicyApplicableService {

    private final RewardPolicyApplicableRepository rewardPolicyApplicableRepository;
    private final ProductManufactorServiceClient applicableClientService;


    @Override
    public RewardPolicyApplicableDto getRewardPolicyApplicable(Long rewardPolicyId, String applicableType) {
        log.info("Fetching reward policy applicable with reward policy id: {}, applicable type: {}",
                rewardPolicyId, applicableType);

        RewardPolicyApplicableEntity applicableEntity = getByRewardPolicyId(rewardPolicyId);
        Long targetId = applicableEntity.getTargetId();

        CommonDto applicableTargetDto = switch (applicableType) {
            case "DEPARTMENT" -> applicableClientService.getDepartment(targetId);
            case "POSITION" -> applicableClientService.getPosition(targetId);
            case "EMPLOYEE" -> applicableClientService.getEmployee(targetId);
            default -> null;
        };

        return RewardPolicyApplicableDto.builder()
                .id(applicableEntity.getId())
                .applicableTarget(applicableTargetDto)
                .build();
    }

    @Override
    public void createRewardPolicyApplicable(Long policyId, Long targetId) {
        log.info("Adding applicable for reward policy with id: {}", policyId);

        RewardPolicyApplicableEntity applicableEntity = RewardPolicyApplicableEntity.builder()
                .targetId(targetId)
                .rewardPolicyId(policyId)
                .build();

        rewardPolicyApplicableRepository.save(applicableEntity);
    }

    @Override
    public void updateRewardPolicyApplicable(Long rewardPolicyId, String applicableType, Long applicableTargetId) {
        log.info("Updating applicable for reward policy with id: {}", rewardPolicyId);

        RewardPolicyApplicableEntity applicableEntity = getByRewardPolicyId(rewardPolicyId);
        applicableEntity.setTargetId(applicableTargetId);

        rewardPolicyApplicableRepository.save(applicableEntity);
    }

    @Override
    public void deleteRewardPolicyApplicable(Long rewardPolicyId) {
        log.info("Deleting applicable for reward policy with id: {}", rewardPolicyId);

        rewardPolicyApplicableRepository.deleteByRewardPolicyId(rewardPolicyId);
    }

    private RewardPolicyApplicableEntity getByRewardPolicyId(Long rewardPolicyId) {
        return rewardPolicyApplicableRepository.findByRewardPolicyId(rewardPolicyId);
    }
}
