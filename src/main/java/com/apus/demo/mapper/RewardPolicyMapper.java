package com.apus.demo.mapper;

import com.apus.demo.dto.RewardPolicyDto;
import com.apus.demo.dto.RewardPolicyListDto;
import com.apus.demo.entity.RewardPolicyEntity;
import com.apus.demo.util.enums.ApplicableType;
import com.apus.demo.util.enums.PolicyType;
import com.apus.demo.util.enums.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RewardPolicyMapper {

    @Mapping(target = "type", expression = "java(buildType(dto.getType()))")
    @Mapping(target = "applicableType", expression = "java(buildApplicableType(dto.getApplicableType()))")
    @Mapping(target = "state", expression = "java(buildState(dto.getState()))")
    RewardPolicyEntity toRewardPolicy(RewardPolicyDto dto);

    @Mapping(target = "type", expression = "java(entity.getType().name())")
    @Mapping(target = "applicableType", expression = "java(entity.getApplicableType().name())")
    @Mapping(target = "state", expression = "java(entity.getState().name())")
    RewardPolicyDto toRewardPolicyDto(RewardPolicyEntity entity);

    @Mapping(target = "type", expression = "java(entity.getType().name())")
    @Mapping(target = "state", expression = "java(entity.getState().name())")
    RewardPolicyListDto toRewardPolicyListDto(RewardPolicyEntity entity);

    default PolicyType buildType(String type) {
        return PolicyType.valueOf(type);
    }

    default ApplicableType buildApplicableType(String type) {
        return ApplicableType.valueOf(type);
    }

    default State buildState(String state) {
        return State.valueOf(state);
    }
}
