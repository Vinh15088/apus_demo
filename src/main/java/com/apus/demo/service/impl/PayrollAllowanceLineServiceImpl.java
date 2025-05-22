package com.apus.demo.service.impl;

import com.apus.demo.dto.AllowanceDto;
import com.apus.demo.dto.GroupAllowanceDto;
import com.apus.demo.dto.PayrollAllowanceLineDto;
import com.apus.demo.dto.PayrollAllowanceListLineDto;
import com.apus.demo.entity.PayrollAllowanceLineEntity;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.mapper.PayrollAllowanceLineMapper;
import com.apus.demo.repository.PayrollAllowanceLineRepository;
import com.apus.demo.service.AllowanceService;
import com.apus.demo.service.GroupAllowanceService;
import com.apus.demo.service.PayrollAllowanceLineService;
import com.apus.demo.util.enums.AmountItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "PAYROLL_ALLOWANCE_LINE_SERVICE")
@RequiredArgsConstructor
public class PayrollAllowanceLineServiceImpl implements PayrollAllowanceLineService {

    private final PayrollAllowanceLineRepository payrollAllowanceLineRepository;
    private final PayrollAllowanceLineMapper payrollAllowanceLineMapper;
    private final AllowanceService allowanceService;
    private final GroupAllowanceService groupAllowanceService;

    @Override
    public List<PayrollAllowanceLineDto> getPayrollAllowanceLines(Long payrollId) {
        log.info("Getting Payroll Allowance Lines for Payroll Id: {}", payrollId);

        List<PayrollAllowanceLineEntity> entities = payrollAllowanceLineRepository.findAllByPayrollId(payrollId);
        List<GroupAllowanceDto> groupAllowancesDto = getListGroupAllowanceDto(entities);
        List<AllowanceDto> allowancesDto = getAllowanceDto(entities);

        Map<Long, AllowanceDto> allowanceMap = allowancesDto.stream().collect(
                Collectors.toMap(AllowanceDto::getId, Function.identity()));

        Map<Long, List<PayrollAllowanceLineEntity>> groupedMap = entities.stream().collect(
                Collectors.groupingBy(PayrollAllowanceLineEntity::getAllowanceGroupId));

        Map<Long, GroupAllowanceDto> groupAllowanceDtoMap = groupAllowancesDto.stream().collect(
                Collectors.toMap(GroupAllowanceDto::getId, Function.identity()));

        return buildPayrollAllowanceLineDto(allowanceMap, groupedMap, groupAllowanceDtoMap);
    }

    @Override
    public void createOrUpdatePayrollAllowanceLines(Long payrollId, List<PayrollAllowanceLineDto> payrollAllowanceLinesDto) {
        log.info("Create or update payroll allowance lines for Payroll Id: {}", payrollId);

        List<PayrollAllowanceLineEntity> existingLines = payrollAllowanceLineRepository.findAllByPayrollId(payrollId);

        Set<Long> newIds = payrollAllowanceLinesDto.stream().map(PayrollAllowanceLineDto::getId).collect(Collectors.toSet());

        // delete all lines that are not in the new list
        List<PayrollAllowanceLineEntity> deleteLines = existingLines.stream()
                .filter(line -> !newIds.contains(line.getId()))
                .toList();

        payrollAllowanceLineRepository.deleteAll(deleteLines);

        // create or update all lines
        List<PayrollAllowanceLineEntity> entities = new ArrayList<>();

        for (PayrollAllowanceLineDto dto : payrollAllowanceLinesDto) {
            Long groupId = dto.getAllowanceGroup().getId();

            for (PayrollAllowanceListLineDto line : dto.getPayrollAllowanceListLines()) {
                PayrollAllowanceLineEntity entity = payrollAllowanceLineMapper.toPayrollAllowanceLine(dto);
                entity.setId(dto.getId());
                entity.setPayrollId(payrollId);
                entity.setAllowanceGroupId(groupId);
                entity.setAllowanceId(line.getAllowance().getId());
                entity.setAmount(line.getAmount());
                entity.setAmountItem(AmountItem.valueOf(line.getAmountItem()));
                entity.setTaxableAmount(line.getTaxableAmount());
                entity.setInsuranceAmount(line.getInsuranceAmount());

                entities.add(entity);
            }
        }

        payrollAllowanceLineRepository.saveAll(entities);
    }

    @Override
    public void deletePayrollAllowanceLines(Long payrollId) {
        log.info("Deleting payroll allowance lines for Payroll Id: {}", payrollId);

        payrollAllowanceLineRepository.deleteAllByPayrollId(payrollId);
    }

    private List<GroupAllowanceDto> getListGroupAllowanceDto(List<PayrollAllowanceLineEntity> entities) {
        Set<Long> allowanceGroupIds = entities.stream().map(PayrollAllowanceLineEntity::getAllowanceGroupId)
                .collect(Collectors.toSet());

        return groupAllowanceService.getGroupAllowancesByIds(allowanceGroupIds);
    }

    private List<AllowanceDto> getAllowanceDto(List<PayrollAllowanceLineEntity> entities) {
        Set<Long> allowanceIds = entities.stream().map(PayrollAllowanceLineEntity::getAllowanceId)
                .collect(Collectors.toSet());

        return allowanceService.getMapAllowanceEntityByIds(allowanceIds);
    }

    private List<PayrollAllowanceLineDto> buildPayrollAllowanceLineDto(Map<Long, AllowanceDto> allowanceMap,
                                                                       Map<Long, List<PayrollAllowanceLineEntity>> groupedMap,
                                                                       Map<Long, GroupAllowanceDto> groupAllowanceDtoMapMap) {
        List<PayrollAllowanceLineDto> result = new ArrayList<>();


        for (Map.Entry<Long, List<PayrollAllowanceLineEntity>> entry : groupedMap.entrySet()) {
            Long groupId = entry.getKey();
            List<PayrollAllowanceLineEntity> lines = entry.getValue();

            List<PayrollAllowanceListLineDto> listLines = lines.stream().map(line -> {
                return PayrollAllowanceListLineDto.builder()
                        .allowance(CommonDtoMapper.toCommonDto(allowanceMap.get(line.getAllowanceId())))
                        .amount(line.getAmount())
                        .amountItem(line.getAmountItem().name())
                        .taxableAmount(line.getTaxableAmount())
                        .insuranceAmount(line.getInsuranceAmount())
                        .build();

            }).toList();

            PayrollAllowanceLineDto dto = new PayrollAllowanceLineDto();
            dto.setAllowanceGroup(CommonDtoMapper.toCommonDto(groupAllowanceDtoMapMap.get(groupId)));
            dto.setPayrollAllowanceListLines(listLines);

            result.add(dto);
        }

        return result;
    }

}
