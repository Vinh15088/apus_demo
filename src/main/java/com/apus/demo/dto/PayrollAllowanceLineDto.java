package com.apus.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollAllowanceLineDto {

    private Long id;

    private CommonDto allowanceGroup;

    private List<PayrollAllowanceListLineDto> payrollAllowanceListLines;
}
