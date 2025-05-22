package com.apus.demo.mapper;

import com.apus.demo.dto.RewardPolicyLineDto;
import com.apus.demo.entity.RewardPolicyLineEntity;
import com.apus.demo.util.enums.Cycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RewardPolicyLineMapper {
    @Mapping(target = "rewardId", source = "reward.id")
    @Mapping(target = "cycle", expression = "java(buildCycle(dto.getCycle()))")
    RewardPolicyLineEntity toRewardPolicyLine(RewardPolicyLineDto dto);

    @Mapping(target = "reward.id", source = "rewardId")
    @Mapping(target = "cycle", expression = "java(entity.getCycle().name())")
    RewardPolicyLineDto toRewardPolicyLineDto(RewardPolicyLineEntity entity);

    default Cycle buildCycle(String cycle) {
        return Cycle.valueOf(cycle);
    }
}
