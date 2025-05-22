package com.apus.demo.controller;

import com.apus.demo.dto.*;
import com.apus.demo.dto.request.AllowancePolicySearchRequest;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import com.apus.demo.service.AllowancePolicyService;
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

@RestController
@RequestMapping("/allowance-policy")
@RequiredArgsConstructor
@Slf4j(topic = "ALLOWANCE_POLICY_CONTROLLER")
@Validated
@Tag(name = "ALLOWANCE_POLICY", description = "APIs for managing allowance policy")
public class AllowancePolicyController {

    private final AllowancePolicyService allowancePolicyService;
    private final MessageUtil messageUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create a new allowance policy")
    public BaseResponse<CommonDto> createAllowancePolicy(@Valid @RequestBody AllowancePolicyDto allowancePolicyDto) {
        log.info("Creating allowance policy");

        CommonDto commonDto = allowancePolicyService.createAllowancePolicy(allowancePolicyDto);

        return BaseResponse.success(messageUtil.getMessage("response.success"), commonDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get a allowance policy by ID")
    public BaseResponse<AllowancePolicyDto> getAllowancePolicy(@RequestParam Long id) {
        log.info("Fetching allowance policy with id: {}", id);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                allowancePolicyService.getAllowancePolicy(id));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all allowance policies with pagination")
    public BaseResponse<PageResponse<AllowancePolicyListDto>> getListAllowancePolicies(
            @ParameterObject AllowancePolicySearchRequest request) {
        log.info("Get all or searching allowance policies with criteria: {}", request);

        AllowancePolicySearchCriteria criteria = request.toAllowancePolicySearchCriteria();

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 20 : request.getSize();
        String sortBy = request.getSort() == null ? "id" : request.getSort();
        String sortDirection = request.getSortDirection() == null ? "DESC" : request.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Page<AllowancePolicyListDto> allowancePolicyPage = allowancePolicyService.getListAllowancePolicy(criteria, PageRequest.of(page, size, sort));

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                PageResponse.from(allowancePolicyPage));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "PUT", summary = "Update a allowance policy by ID")
    public BaseResponse<CommonDto> updateAllowancePolicy(@Valid @RequestBody AllowancePolicyDto allowancePolicyDto) {
        log.info("Updating allowance policy with id: {}", allowancePolicyDto.getId());

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                allowancePolicyService.updateAllowancePolicy(allowancePolicyDto));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = "DELETE", summary = "Delete a allowance policy by ID")
    public BaseResponse<CommonDto> deleteAllowancePolicy(@RequestParam Long id) {
        log.info("Deleting allowance policy with id: {}", id);

        allowancePolicyService.deleteAllowancePolicy(id);

        return BaseResponse.success(messageUtil.getMessage("response.success"), null);
    }
}
