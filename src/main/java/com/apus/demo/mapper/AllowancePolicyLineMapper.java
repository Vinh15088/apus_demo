package com.apus.demo.mapper;

import com.apus.demo.dto.AllowancePolicyLineDto;
import com.apus.demo.entity.AllowancePolicyLineEntity;
import com.apus.demo.util.enums.Cycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllowancePolicyLineMapper {

    @Mapping(target = "allowanceId", source = "allowance.id")
    @Mapping(target = "cycle", expression = "java(buildCycle(dto.getCycle()))")
    AllowancePolicyLineEntity toAllowancePolicyLine(AllowancePolicyLineDto dto);

    @Mapping(target = "allowance.id", source = "allowanceId")
    @Mapping(target = "cycle", expression = "java(entity.getCycle().name())")
    AllowancePolicyLineDto toAllowancePolicyLineDto(AllowancePolicyLineEntity entity);

    default Cycle buildCycle(String cycle) {
        return Cycle.valueOf(cycle);
    }
}
