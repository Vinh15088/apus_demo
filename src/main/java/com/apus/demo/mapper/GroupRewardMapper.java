package com.apus.demo.mapper;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.GroupRewardDto;
import com.apus.demo.entity.GroupRewardEntity;
import com.apus.demo.repository.GroupRewardRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupRewardMapper {
    @Mapping(source = "parent.id", target = "parentId")
    GroupRewardEntity toGroupReward(GroupRewardDto groupRewardDto);

    @Mapping(target = "parent", expression = "java(buildParentObject(groupReward, groupRewardRepository))")
    GroupRewardDto toGroupRewardDto(GroupRewardEntity groupReward,
                                          @Context GroupRewardRepository groupRewardRepository);


    default CommonDto buildParentObject(GroupRewardEntity groupReward,
                                     @Context GroupRewardRepository groupRewardRepository) {
        if (groupReward == null || groupReward.getParentId() == null) return null;

        GroupRewardEntity parent = groupRewardRepository.findById(groupReward.getParentId()).orElse(null);
        if (parent == null) return null;

        return CommonDto.builder()
                .id(parent.getId())
                .code(parent.getCode())
                .name(parent.getName())
                .build();
    }
}
