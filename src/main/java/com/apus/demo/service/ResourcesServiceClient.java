package com.apus.demo.service;

import com.apus.demo.client.product.UomClient;
import com.apus.demo.dto.CommonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CLIENT_SERVICE")
public class ResourcesServiceClient {

    private final UomClient uomClient;

    public CommonDto getUom(Long id) {
        return uomClient.getUom(id).getData();
    }

    public Map<Long, CommonDto> getMapUomEntityByIds(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) return Collections.emptyMap();

        List<CommonDto> uomDtoList = uomClient.getListUom(ids).getData().getContent();

        return uomDtoList.stream().collect(Collectors.toMap(CommonDto::getId, Function.identity()));
    }

}
