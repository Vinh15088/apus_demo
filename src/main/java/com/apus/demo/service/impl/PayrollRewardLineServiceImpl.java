package com.apus.demo.service.impl;

import com.apus.demo.dto.*;
import com.apus.demo.entity.PayrollRewardLineEntity;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.mapper.PayrollRewardLineMapper;
import com.apus.demo.repository.PayrollRewardLineRepository;
import com.apus.demo.service.GroupRewardService;
import com.apus.demo.service.PayrollRewardLineService;
import com.apus.demo.service.RewardService;
import com.apus.demo.util.enums.AmountItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "PAYROLL_REWARD_LINE_SERVICE")
@RequiredArgsConstructor
public class PayrollRewardLineServiceImpl implements PayrollRewardLineService {

    private final PayrollRewardLineRepository payrollRewardLineRepository;
    private final PayrollRewardLineMapper payrollRewardLineMapper;
    private final RewardService rewardService;
    private final GroupRewardService groupRewardService;


    @Override
    public List<PayrollRewardLineDto> getPayrollRewardLines(Long payrollId) {
        log.info("Getting payroll reward lines for payroll Id: {}", payrollId);

        List<PayrollRewardLineEntity> entities = payrollRewardLineRepository.findAllByPayrollId(payrollId);
        List<GroupRewardDto> groupRewardsDto = getListGroupRewardDto(entities);
        List<RewardDto> rewardsDto = getRewardDto(entities);

        Map<Long, RewardDto> rewardMap = rewardsDto.stream().collect(
                Collectors.toMap(RewardDto::getId, Function.identity()));

        Map<Long, List<PayrollRewardLineEntity>> groupedMap = entities.stream().collect(
                Collectors.groupingBy(PayrollRewardLineEntity::getRewardGroupId));

        Map<Long, GroupRewardDto> groupRewardDtoMap = groupRewardsDto.stream().collect(
                Collectors.toMap(GroupRewardDto::getId, Function.identity()));

        return buildPayrollRewardLineDto(rewardMap, groupedMap, groupRewardDtoMap);
    }

    @Override
    public void createOrUpdatePayrollRewardLines(Long payrollId, List<PayrollRewardLineDto> payrollRewardLinesDto) {
        log.info("Create or update payroll reward lines for Payroll Id: {}", payrollId);

        List<PayrollRewardLineEntity> existingLines = payrollRewardLineRepository.findAllByPayrollId(payrollId);

        Set<Long> newIds = payrollRewardLinesDto.stream().map(PayrollRewardLineDto::getId).collect(Collectors.toSet());

        // delete all lines that are not in the new list
        List<PayrollRewardLineEntity> deleteLines = existingLines.stream()
                .filter(line -> !newIds.contains(line.getId()))
                .toList();

        payrollRewardLineRepository.deleteAll(deleteLines);

        // create or update all lines
        List<PayrollRewardLineEntity> entities = new ArrayList<>();

        for (PayrollRewardLineDto dto : payrollRewardLinesDto) {
            Long groupId = dto.getRewardGroup().getId();

            for (PayrollRewardListLineDto line : dto.getPayrollRewardListLines()) {
                PayrollRewardLineEntity entity = payrollRewardLineMapper.toPayrollRewardLine(dto);
                entity.setId(dto.getId());
                entity.setPayrollId(payrollId);
                entity.setRewardGroupId(groupId);
                entity.setRewardId(line.getReward().getId());
                entity.setAmount(line.getAmount());
                entity.setAmountItem(AmountItem.valueOf(line.getAmountItem()));
                entity.setTaxableAmount(line.getTaxableAmount());
                entity.setInsuranceAmount(line.getInsuranceAmount());

                entities.add(entity);
            }
        }

        payrollRewardLineRepository.saveAll(entities);
    }

    @Override
    public void deletePayrollRewardLines(Long payrollId) {
        log.info("Deleting payroll reward lines for Payroll Id: {}", payrollId);

        payrollRewardLineRepository.deleteAllByPayrollId(payrollId);
    }

    private List<GroupRewardDto> getListGroupRewardDto(List<PayrollRewardLineEntity> entities) {
        Set<Long> rewardGroupIds = entities.stream().map(PayrollRewardLineEntity::getRewardGroupId)
                .collect(Collectors.toSet());

        return groupRewardService.getGroupAllowanceEntityByIds(rewardGroupIds);
    }

    private List<RewardDto> getRewardDto(List<PayrollRewardLineEntity> entities) {
        Set<Long> rewardIds = entities.stream().map(PayrollRewardLineEntity::getRewardId)
                .collect(Collectors.toSet());

        return rewardService.getMapRewardEntityByIds(rewardIds);
    }

    private List<PayrollRewardLineDto> buildPayrollRewardLineDto(Map<Long, RewardDto> rewardMap,
                                                                       Map<Long, List<PayrollRewardLineEntity>> groupedMap,
                                                                       Map<Long, GroupRewardDto> groupRewardDtoMapMap) {
        List<PayrollRewardLineDto> result = new ArrayList<>();


        for (Map.Entry<Long, List<PayrollRewardLineEntity>> entry : groupedMap.entrySet()) {
            Long groupId = entry.getKey();
            List<PayrollRewardLineEntity> lines = entry.getValue();

            List<PayrollRewardListLineDto> listLines = lines.stream().map(line -> {
                return PayrollRewardListLineDto.builder()
                        .reward(CommonDtoMapper.toCommonDto(rewardMap.get(line.getRewardId())))
                        .amount(line.getAmount())
                        .amountItem(line.getAmountItem().name())
                        .taxableAmount(line.getTaxableAmount())
                        .insuranceAmount(line.getInsuranceAmount())
                        .build();

            }).toList();

            PayrollRewardLineDto dto = new PayrollRewardLineDto();
            dto.setRewardGroup(CommonDtoMapper.toCommonDto(groupRewardDtoMapMap.get(groupId)));
            dto.setPayrollRewardListLines(listLines);

            result.add(dto);
        }

        return result;
    }
}
