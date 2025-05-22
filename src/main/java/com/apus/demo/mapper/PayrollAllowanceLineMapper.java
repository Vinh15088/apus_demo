package com.apus.demo.mapper;

import com.apus.demo.dto.PayrollAllowanceLineDto;
import com.apus.demo.entity.PayrollAllowanceLineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollAllowanceLineMapper {

    PayrollAllowanceLineEntity toPayrollAllowanceLine(PayrollAllowanceLineDto dto);

    PayrollAllowanceLineDto toPayrollAllowanceLineDto(PayrollAllowanceLineEntity entity);

}
