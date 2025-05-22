package com.apus.demo.mapper;

import com.apus.demo.dto.AllowancePolicyDto;
import com.apus.demo.dto.AllowancePolicyListDto;
import com.apus.demo.entity.AllowancePolicyEntity;
import com.apus.demo.util.enums.ApplicableType;
import com.apus.demo.util.enums.PolicyType;
import com.apus.demo.util.enums.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AllowancePolicyMapper {

    @Mapping(target = "type", expression = "java(buildType(dto.getType()))")
    @Mapping(target = "applicableType", expression = "java(buildApplicableType(dto.getApplicableType()))")
    @Mapping(target = "state", expression = "java(buildState(dto.getState()))")
    AllowancePolicyEntity toAllowancePolicy(AllowancePolicyDto dto);

    @Mapping(target = "type", expression = "java(entity.getType().name())")
    @Mapping(target = "applicableType", expression = "java(entity.getApplicableType().name())")
    @Mapping(target = "state", expression = "java(entity.getState().name())")
    AllowancePolicyDto toAllowancePolicyDto(AllowancePolicyEntity entity);

    @Mapping(target = "type", expression = "java(entity.getType().name())")
    @Mapping(target = "state", expression = "java(entity.getState().name())")
    AllowancePolicyListDto toAllowancePolicyListDto(AllowancePolicyEntity entity);

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
