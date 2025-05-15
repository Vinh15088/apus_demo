package com.apus.demo.mapper;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.entity.GroupAllowanceEntity;
import com.apus.demo.repository.GroupAllowanceRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupAllowanceMapper {

    @Mapping(source = "parent.id", target = "parentId")
    GroupAllowanceEntity toGroupAllowance(GroupAllowanceDto groupAllowanceDto);

    @Mapping(target = "parent", expression = "java(buildParentObject(groupAllowanceEntity, groupAllowanceRepository))")
    GroupAllowanceDto toGroupAllowanceDto(GroupAllowanceEntity groupAllowanceEntity,
                                         @Context GroupAllowanceRepository groupAllowanceRepository);


    default CommonDto buildParentObject(GroupAllowanceEntity groupAllowance,
                                     @Context GroupAllowanceRepository groupAllowanceRepository) {
        if (groupAllowance == null || groupAllowance.getParentId() == null) return null;

        GroupAllowanceEntity parent = groupAllowanceRepository.findById(groupAllowance.getParentId()).orElse(null);
        if (parent == null) return null;

        return CommonDto.builder()
            .id(parent.getId())
            .code(parent.getCode())
            .name(parent.getName())
            .build();
    }
}
