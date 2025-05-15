package com.apus.demo.mapper;

import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.entity.GroupAllowanceEntity;
import com.apus.demo.repository.GroupAllowanceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupAllowanceMapper {

    @Mapping(target = "parentId", expression = "java(buildParentId(groupAllowanceDto))")
    GroupAllowanceEntity toGroupAllowance(GroupAllowanceDto groupAllowanceDto);

    @Mapping(target = "parent", expression = "java(buildParentObject(groupAllowanceEntity, groupAllowanceRepository))")
    GroupAllowanceDto toGroupAllowanceDto(GroupAllowanceEntity groupAllowanceEntity,
                                         @Context GroupAllowanceRepository groupAllowanceRepository);

    default Long buildParentId(GroupAllowanceDto groupAllowanceDto) {
        if (groupAllowanceDto.getParent() == null) return null;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(groupAllowanceDto.getParent(), JsonNode.class);
        
        if (!node.has("id")) return null;

        return node.get("id").asLong();
    }

    default Object buildParentObject(GroupAllowanceEntity groupAllowance,
                                     @Context GroupAllowanceRepository groupAllowanceRepository) {
        if (groupAllowance == null || groupAllowance.getParentId() == null) return null;

        GroupAllowanceEntity parent = groupAllowanceRepository.findById(groupAllowance.getParentId()).orElse(null);
        if (parent == null) return null;

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("id", parent.getId());
        node.put("code", parent.getCode());
        node.put("name", parent.getName());

        return node;
    }
}
