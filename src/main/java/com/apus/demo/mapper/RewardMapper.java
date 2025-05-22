package com.apus.demo.mapper;

import com.apus.demo.dto.RewardDto;
import com.apus.demo.dto.RewardListDto;
import com.apus.demo.entity.RewardEntity;
import com.apus.demo.util.enums.AllowanceRewardType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RewardMapper {

    @Mapping(target = "includeType", expression = "java(buildIncludeTypeOfEntity(rewardDto.getIncludeType()))")
    @Mapping(target = "type", expression = "java(buildAllowanceRewardType(rewardDto.getType()))")
    @Mapping(target = "uomId", source = "uom.id")
    @Mapping(target = "currencyId", source = "currency.id")
    @Mapping(target = "groupRewardId", source = "groupReward.id")
    RewardEntity toReward(RewardDto rewardDto);

    @Mapping(target = "includeType", expression = "java(buildIncludeTypeOfDto(rewardEntity.getIncludeType()))")
    @Mapping(target = "type", expression = "java(rewardEntity.getType().name())")
    @Mapping(target = "uom.id", source = "uomId")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "groupReward.id", source = "groupRewardId")
    RewardDto toRewardDto(RewardEntity rewardEntity);

    RewardListDto toRewardListDto(RewardEntity rewardEntity);

    default String buildIncludeTypeOfEntity(Set<String> includeType) {
        return includeType.stream().collect(Collectors.joining(","));
    }

    default Set<String> buildIncludeTypeOfDto(String includeType) {
        if(includeType == null || includeType.isEmpty()) return Collections.emptySet();

        return Arrays.stream(includeType.split(","))
                .map(String::trim)
                .map(String::valueOf)
                .collect(Collectors.toSet());
    }

    default AllowanceRewardType buildAllowanceRewardType(String type) {
        return AllowanceRewardType.valueOf(type);
    }
}
