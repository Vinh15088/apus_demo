package com.apus.demo.mapper;

import com.apus.demo.dto.PayrollDto;
import com.apus.demo.dto.PayrollListDto;
import com.apus.demo.entity.PayrollEntity;
import com.apus.demo.util.enums.Cycle;
import com.apus.demo.util.enums.PayrollType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PayrollMapper {

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "payrollType", expression = "java(buildPayrollType(dto.getPayrollType()))")
    @Mapping(target = "cycle", expression = "java(buildCycle(dto.getCycle()))")
    PayrollEntity toPayroll(PayrollDto dto);

    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "department.id", source = "departmentId")
    @Mapping(target = "position.id", source = "positionId")
    @Mapping(target = "payrollType", expression = "java(entity.getPayrollType().name())")
    @Mapping(target = "cycle", expression = "java(entity.getCycle().name())")
    PayrollDto toPayrollDto(PayrollEntity entity);

    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "department.id", source = "departmentId")
    @Mapping(target = "position.id", source = "positionId")
    @Mapping(target = "payrollType", expression = "java(entity.getPayrollType().name())")
    @Mapping(target = "cycle", expression = "java(entity.getCycle().name())")
    PayrollListDto toPayrollListDto(PayrollEntity entity);

    default PayrollType buildPayrollType(String type) {
        return PayrollType.valueOf(type);
    }

    default Cycle buildCycle(String cycle) {
        return Cycle.valueOf(cycle);
    }
}
