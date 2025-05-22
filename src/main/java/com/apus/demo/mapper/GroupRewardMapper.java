package com.apus.demo.mapper;

import com.apus.demo.dto.GroupRewardDto;
import com.apus.demo.entity.GroupRewardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupRewardMapper {
    @Mapping(source = "parent.id", target = "parentId")
    GroupRewardEntity toGroupReward(GroupRewardDto groupRewardDto);

    @Mapping(source = "parentId", target = "parent.id")
    GroupRewardDto toGroupRewardDto(GroupRewardEntity groupReward);

}
