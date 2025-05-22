package com.apus.demo.mapper;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.entity.IdentifiableEntity;

public class CommonDtoMapper {

    public static CommonDto toCommonDto(IdentifiableEntity entity) {
        if(entity == null) return null;

        return CommonDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .build();
    }
}
