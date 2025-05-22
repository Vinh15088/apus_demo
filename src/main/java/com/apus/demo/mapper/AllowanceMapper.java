package com.apus.demo.mapper;

import com.apus.demo.dto.AllowanceDto;
import com.apus.demo.dto.AllowanceListDto;
import com.apus.demo.entity.AllowanceEntity;
import com.apus.demo.util.enums.AllowanceRewardType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AllowanceMapper {

    @Mapping(target = "includeType", expression = "java(buildIncludeTypeOfEntity(allowanceDto.getIncludeType()))")
    @Mapping(target = "type", expression = "java(buildAllowanceRewardType(allowanceDto.getType()))")
    @Mapping(target = "uomId", source = "uom.id")
    @Mapping(target = "currencyId", source = "currency.id")
    @Mapping(target = "groupAllowanceId", source = "groupAllowance.id")
    AllowanceEntity toAllowance(AllowanceDto allowanceDto);

    @Mapping(target = "includeType", expression = "java(buildIncludeTypeOfDto(allowanceEntity.getIncludeType()))")
    @Mapping(target = "type", expression = "java(allowanceEntity.getType().name())")
    @Mapping(target = "uom.id", source = "uomId")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "groupAllowance.id", source = "groupAllowanceId")
    AllowanceDto toAllowanceDto(AllowanceEntity allowanceEntity);

    @Mapping(target = "groupAllowance.id", source = "groupAllowanceId")
    AllowanceListDto toAllowanceListDto(AllowanceEntity allowanceEntity);

    default String buildIncludeTypeOfEntity(Set<String> includeType) {
        return String.join(",", includeType);
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
