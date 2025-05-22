package com.apus.demo.service;

import com.apus.demo.client.resources.CurrencyClient;
import com.apus.demo.client.resources.DepartmentClient;
import com.apus.demo.client.resources.EmployeeClient;
import com.apus.demo.client.resources.PositionClient;
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
@Slf4j(topic = "APPLICABLE_CLIENT_SERVICE")
@RequiredArgsConstructor
public class ProductManufactorServiceClient {

    private final DepartmentClient departmentClient;
    private final PositionClient positionClient;
    private final EmployeeClient employeeClient;
    private final CurrencyClient currencyClient;

    public CommonDto getDepartment(Long id) {
        return departmentClient.getDepartment(id).getData();
    }

    public CommonDto getPosition(Long id) {
        return positionClient.getPosition(id).getData();
    }

    public CommonDto getEmployee(Long id) {
        return employeeClient.getEmployee(id).getData();
    }

    public CommonDto getCurrency(Long id) {
        return currencyClient.getCurrency(id).getData();
    }

    public Map<Long, CommonDto> getMapDepartmentEntityByIds(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) return Collections.emptyMap();

        List<CommonDto> departmentDtoList = departmentClient.getListDepartment(ids).getData().getContent();

        return departmentDtoList.stream().collect(Collectors.toMap(CommonDto::getId, Function.identity()));
    }

    public Map<Long, CommonDto> getMapPositionEntityByIds(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) return Collections.emptyMap();

        List<CommonDto> positionDtoList = positionClient.getListPosition(ids).getData().getContent();

        return positionDtoList.stream().collect(Collectors.toMap(CommonDto::getId, Function.identity()));
    }

    public Map<Long, CommonDto> getMapEmployeeEntityByIds(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) return Collections.emptyMap();

        List<CommonDto> employeeDtoList = employeeClient.getListEmployee(ids).getData().getContent();

        return employeeDtoList.stream().collect(Collectors.toMap(CommonDto::getId, Function.identity()));
    }

    public Map<Long, CommonDto> getMapCurrencyEntityByIds(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) return Collections.emptyMap();

        List<CommonDto> currencyDtoList = currencyClient.getListCurrency(ids).getData().getContent();

        return currencyDtoList.stream().collect(Collectors.toMap(CommonDto::getId, Function.identity()));
    }
}
