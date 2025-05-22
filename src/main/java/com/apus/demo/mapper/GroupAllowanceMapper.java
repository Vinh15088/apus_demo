package com.apus.demo.mapper;

import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.entity.GroupAllowanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupAllowanceMapper {

    @Mapping(source = "parent.id", target = "parentId")
    GroupAllowanceEntity toGroupAllowance(GroupAllowanceDto groupAllowanceDto);

    @Mapping(source = "parentId", target = "parent.id")
    GroupAllowanceDto toGroupAllowanceDto(GroupAllowanceEntity groupAllowanceEntity);

}
