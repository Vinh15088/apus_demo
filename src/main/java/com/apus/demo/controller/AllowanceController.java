package com.apus.demo.controller;

import com.apus.demo.dto.AllowanceDto;
import com.apus.demo.dto.AllowanceListDto;
import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.CommonSearchCriteria;
import com.apus.demo.dto.request.CommonSearchRequest;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import com.apus.demo.service.AllowanceService;
import com.apus.demo.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/allowance")
@RequiredArgsConstructor
@Slf4j(topic = "ALLOWANCE_CONTROLLER")
@Validated
@Tag(name = "ALLOWANCE", description = "APIs for managing allowance")
public class AllowanceController {

    private final AllowanceService allowanceService;
    private final MessageUtil messageUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create a new allowance")
    public BaseResponse<CommonDto> createAllowance(@Valid @RequestBody AllowanceDto allowanceDto) {
        log.info("Creating allowance: {}", allowanceDto);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                allowanceService.createAllowance(allowanceDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get a allowance by ID")
    public BaseResponse<AllowanceDto> getAllowance(@RequestParam Long id) {
        log.info("Fetching allowance with id: {}", id);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                allowanceService.getAllowance(id));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all allowances with pagination")
    public BaseResponse<PageResponse<AllowanceListDto>> getListAllowances(@ParameterObject CommonSearchRequest request) {
        log.info("Get all or searching allowances with criteria: {}", request);

        CommonSearchCriteria criteria = request.toCommonSearchCriteria();

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 20 : request.getSize();
        String sortBy = request.getSort() == null ? "id" : request.getSort();
        String sortDirection = request.getSortDirection() == null ? "DESC" : request.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Page<AllowanceListDto> allowancePage = allowanceService.getListAllowances(criteria, PageRequest.of(page, size, sort));

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                PageResponse.from(allowancePage));
    }

    @GetMapping("list/ids")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all allowances by ids")
    public BaseResponse<List<AllowanceDto>> searchAllowancesByIds(@RequestParam Set<Long> ids) {
        log.info("Get all or searching allowances by ids: {}", ids);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                allowanceService.getMapAllowanceEntityByIds(ids));
    }

    @PutMapping
    @Operation(method = "PUT", summary = "Update a allowance by ID")
    public BaseResponse<CommonDto> updateAllowance(@Valid @RequestBody AllowanceDto allowanceDto) {
        log.info("Updating allowance with id: {}", allowanceDto.getId());

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                allowanceService.updateAllowance(allowanceDto));
    }

    @DeleteMapping
    @Operation(method = "DELETE", summary = "Delete a allowance by ID")
    public BaseResponse<Void> deleteAllowance(@RequestParam Long id) {
        log.info("Deleting allowance with id: {}", id);

        allowanceService.deleteAllowance(id);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                null);
    }
}
