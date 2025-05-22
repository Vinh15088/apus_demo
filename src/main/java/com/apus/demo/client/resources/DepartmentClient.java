package com.apus.demo.client.resources;

import com.apus.demo.config.ClientConfig;
import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(
    name = "department",
    url = "${client.resources-service.url}",
    path = "${client.resources-service.path}",
    configuration = ClientConfig.class
)
public interface DepartmentClient {
    @GetMapping("/department")
    BaseResponse<CommonDto> getDepartment(@RequestParam Long departmentId);

    @GetMapping("/department/list")
    BaseResponse<PageResponse<CommonDto>> getListDepartment(@RequestParam Set<Long> ids);
}
