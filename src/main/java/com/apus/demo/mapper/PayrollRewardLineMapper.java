package com.apus.demo.mapper;

import com.apus.demo.dto.PayrollRewardLineDto;
import com.apus.demo.entity.PayrollRewardLineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollRewardLineMapper {

    PayrollRewardLineEntity toPayrollRewardLine(PayrollRewardLineDto dto);

    PayrollRewardLineDto toPayrollRewardLineDto(PayrollRewardLineEntity entity);

}
