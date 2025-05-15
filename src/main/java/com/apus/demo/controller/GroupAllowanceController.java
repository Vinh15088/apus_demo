package com.apus.demo.controller;

import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.dto.GroupAllowanceSearchCriteria;
import com.apus.demo.dto.request.GroupAllowanceSearchRequest;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import com.apus.demo.service.GroupAllowanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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


@RestController
@RequestMapping("/group-allowance")
@RequiredArgsConstructor
@Slf4j(topic = "GROUP_ALLOWANCE_CONTROLLER")
@Validated
@Tag(name = "GROUP ALLOWANCE", description = "APIs for managing group allowances")
public class GroupAllowanceController {

    private final GroupAllowanceService groupAllowanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create a new group allowance")
    public BaseResponse<Object> createGroupAllowance(@Valid @RequestBody GroupAllowanceDto groupAllowanceDto) {

        log.info("Creating group allowance: {}", groupAllowanceDto);

        Long id = groupAllowanceService.addGroupAllowance(groupAllowanceDto);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", id);

        return BaseResponse.success(node);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get a group allowance by ID")
    public BaseResponse<GroupAllowanceDto> getGroupAllowance(@RequestParam Long id) {
        log.info("Fetching group allowance with id: {}", id);

        return BaseResponse.success(groupAllowanceService.getGroupAllowance(id));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all group allowances with pagination")
    public BaseResponse<PageResponse<GroupAllowanceDto>> searchGroupAllowances(@ParameterObject GroupAllowanceSearchRequest request) {

        log.info("Get all or searching group allowances with criteria: {}", request);

        GroupAllowanceSearchCriteria criteria = request.toGroupAllowanceSearchCriteria();

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 20 : request.getSize();
        String sortBy = request.getSort() == null ? "createdAt" : request.getSort();
        String sortDirection = request.getSortDirection() == null ? "DESC" : request.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Page<GroupAllowanceDto> groupAllowancePage = groupAllowanceService.searchGroupAllowances(
            criteria,
            PageRequest.of(page, size, sort)
        );

        return BaseResponse.success(PageResponse.from(groupAllowancePage));
    }

    @PutMapping
    @Operation(method = "PUT", summary = "Update a group allowance")
    public BaseResponse<Object> updateGroupAllowance(@Valid @RequestBody GroupAllowanceDto groupAllowanceDto) {

        log.info("Updating group allowance with id: {}", groupAllowanceDto.getId());

        groupAllowanceService.updateGroupAllowance(groupAllowanceDto);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", groupAllowanceDto.getId());

        return BaseResponse.success(node);
    }

    @DeleteMapping
    @Operation(method = "DELETE", summary = "Delete a group allowance")
    public BaseResponse<Void> deleteGroupAllowance(@RequestParam Long id) {

        log.info("Deleting group allowance with id: {}", id);

        groupAllowanceService.deleteGroupAllowance(id);

        return BaseResponse.success(null);
    }
} 