package com.apus.demo.client.product;

import com.apus.demo.config.ClientConfig;
import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(
        name = "uom",
        url = "${client.product-manufactor-service.url}",
        path = "${client.product-manufactor-service.path}",
        configuration = ClientConfig.class
)
public interface UomClient {

    @GetMapping("/unit")
    BaseResponse<CommonDto> getUom(@RequestParam Long unitId);

    @GetMapping("/unit/list")
    BaseResponse<PageResponse<CommonDto>> getListUom(@RequestParam Set<Long> ids);
}
