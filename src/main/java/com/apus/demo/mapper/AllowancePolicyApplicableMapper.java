package com.apus.demo.mapper;

import com.apus.demo.dto.AllowancePolicyApplicableDto;
import com.apus.demo.entity.AllowancePolicyApplicableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllowancePolicyApplicableMapper {

    @Mapping(target = "targetId", source = "applicableTarget.id")
    AllowancePolicyApplicableEntity toAllowancePolicyApplicable(AllowancePolicyApplicableDto dto);

    @Mapping(target = "applicableTarget.id", source = "targetId")
    AllowancePolicyApplicableDto toAllowancePolicyApplicableDto(AllowancePolicyApplicableEntity entity);
}
