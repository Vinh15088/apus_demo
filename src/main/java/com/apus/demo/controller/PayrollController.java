package com.apus.demo.controller;

import com.apus.demo.dto.CommonDto;
import com.apus.demo.dto.PayrollDto;
import com.apus.demo.dto.PayrollListDto;
import com.apus.demo.dto.PayrollSearchCriteria;
import com.apus.demo.dto.request.PayrollSearchRequest;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import com.apus.demo.service.PayrollService;
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
@RequestMapping("/payroll")
@RequiredArgsConstructor
@Slf4j(topic = "PAYROLL_CONTROLLER")
@Validated
@Tag(name = "PAYROLL", description = "APIs for managing payroll")
public class PayrollController {

    private final PayrollService payrollService;
    private final MessageUtil messageUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create a new payroll")
    public BaseResponse<CommonDto> createPayroll(@Valid @RequestBody PayrollDto payrollDto) {
        log.info("Creating new payroll");

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                payrollService.createPayroll(payrollDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get a payroll by ID")
    public BaseResponse<PayrollDto> getPayroll(@RequestParam Long id) {
        log.info("Fetching payroll with id: {}", id);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                payrollService.getPayroll(id));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all payrolls with pagination")
    public BaseResponse<PageResponse<PayrollListDto>> getListPayrolls(@ParameterObject PayrollSearchRequest request) {
        log.info("Get all or searching rewards with criteria: {}", request);

        PayrollSearchCriteria criteria = request.toPayrollSearchCriteria();

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 20 : request.getSize();
        String sortBy = request.getSort() == null ? "id" : request.getSort();
        String sortDirection = request.getSortDirection() == null ? "DESC" : request.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Page<PayrollListDto> payrollPage = payrollService.getListPayrolls(criteria, PageRequest.of(page, size, sort));

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                PageResponse.from(payrollPage));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "PUT", summary = "Update a payroll by ID")
    public BaseResponse<CommonDto> updatePayroll(@Valid @RequestBody PayrollDto payrollDto) {
        log.info("Updating payroll with id: {}", payrollDto.getId());

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                payrollService.updatePayroll(payrollDto));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = "DELETE", summary = "Delete a payroll by ID")
    public BaseResponse<CommonDto> deletePayroll(@RequestParam Long id) {
        log.info("Deleting payroll with id: {}", id);

        payrollService.deletePayroll(id);

        return BaseResponse.success(messageUtil.getMessage("response.success"), null);
    }
}
