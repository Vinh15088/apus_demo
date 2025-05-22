package com.apus.demo.service.impl;

import com.apus.demo.dto.*;
import com.apus.demo.entity.PayrollEntity;
import com.apus.demo.mapper.CommonDtoMapper;
import com.apus.demo.mapper.PayrollMapper;
import com.apus.demo.repository.PayrollRepository;
import com.apus.demo.repository.specification.PayrollSpecification;
import com.apus.demo.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "PAYROLL_SERVICE")
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final PayrollMapper payrollMapper;
    private final PayrollAllowanceLineService payrollAllowanceLineService;
    private final PayrollRewardLineService payrollRewardLineService;
    private final GroupAllowanceService groupAllowanceService;
    private final GroupRewardService groupRewardService;
    private final ProductManufactorServiceClient productManufactorService;

    @Override
    @Transactional
    public CommonDto createPayroll(PayrollDto payrollDto) {
        log.info("Adding new payroll");

        PayrollEntity payroll = payrollMapper.toPayroll(payrollDto);
        payrollRepository.save(payroll);

        // add payroll allowance lines
        payrollAllowanceLineService.createOrUpdatePayrollAllowanceLines(payroll.getId(), payrollDto.getPayrollAllowanceLines());

        // add payroll reward lines
        payrollRewardLineService.createOrUpdatePayrollRewardLines(payroll.getId(), payrollDto.getPayrollRewardLines());

        return CommonDto.builder().id(payroll.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public PayrollDto getPayroll(Long id) {
        log.info("Fetching payroll with id: {}", id);

        PayrollEntity payroll = getById(id);

        PayrollDto payrollDto = payrollMapper.toPayrollDto(payroll);

        // get employee
        CommonDto employee = productManufactorService.getEmployee(payroll.getEmployeeId());
        payrollDto.setEmployee(employee);

        // get department
        CommonDto department = productManufactorService.getDepartment(payroll.getDepartmentId());
        payrollDto.setDepartment(department);

        // get position
        CommonDto position = productManufactorService.getPosition(payroll.getPositionId());
        payrollDto.setPosition(position);

        // get payroll allowance lines
        List<PayrollAllowanceLineDto> allowanceLines = payrollAllowanceLineService.getPayrollAllowanceLines(payroll.getId());
        payrollDto.setPayrollAllowanceLines(allowanceLines);

        // get group allowances
        List<CommonDto> groupAllowancesDto = getGroupAllowancesDto(payrollDto);
        payrollDto.setAllowanceGroups(groupAllowancesDto);

        // get payroll reward lines
        payrollDto.setPayrollRewardLines(payrollRewardLineService.getPayrollRewardLines(payroll.getId()));

        // get group rewards
        List<CommonDto> groupRewardsDto = getGroupRewardsDto(payrollDto);
        payrollDto.setRewardGroups(groupRewardsDto);

        return payrollDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayrollListDto> getListPayrolls(PayrollSearchCriteria criteria, Pageable pageable) {
        log.info("Get list of payrolls with criteria");

        Specification<PayrollEntity> spec = PayrollSpecification.buildSpecification(criteria);

        Page<PayrollEntity> payrollEntityPage = payrollRepository.findAll(spec, pageable);

        return payrollEntityPage.map(payrollMapper::toPayrollListDto);
    }

    @Override
    @Transactional
    public CommonDto updatePayroll(PayrollDto payrollDto) {
        log.info("Updating payroll with id: {}", payrollDto.getId());

        PayrollEntity payroll = payrollMapper.toPayroll(payrollDto);
        payroll.setId(payrollDto.getId());
        payrollRepository.save(payroll);

        // update payroll allowance lines
        payrollAllowanceLineService.createOrUpdatePayrollAllowanceLines(payroll.getId(), payrollDto.getPayrollAllowanceLines());

        // update payroll reward lines
        payrollRewardLineService.createOrUpdatePayrollRewardLines(payroll.getId(), payrollDto.getPayrollRewardLines());

        log.info("Successfully updated payroll with id: {}", payroll.getId());

        return CommonDto.builder().id(payroll.getId()).build();
    }

    @Override
    @Transactional
    public void deletePayroll(Long id) {
        log.info("Deleting payroll with id: {}", id);

        payrollRepository.delete(getById(id));

        // delete payroll allowance lines
        payrollAllowanceLineService.deletePayrollAllowanceLines(id);

        // delete payroll reward lines
        payrollRewardLineService.deletePayrollRewardLines(id);
    }

    private PayrollEntity getById(Long id) {
        return payrollRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Payroll not found with id: " + id));
    }

    private List<CommonDto> getGroupAllowancesDto(PayrollDto payrollDto) {
        Set<Long> idsOfGroupAllowanceLines = payrollDto.getPayrollAllowanceLines().stream()
                .map(dto -> {
                    return dto.getAllowanceGroup().getId();
                })
                .collect(Collectors.toSet());


        List<GroupAllowanceDto> groupAllowances = groupAllowanceService.getGroupAllowancesByIds(idsOfGroupAllowanceLines);

        return groupAllowances.stream().map(CommonDtoMapper::toCommonDto).collect(Collectors.toList());
    }

    private List<CommonDto> getGroupRewardsDto(PayrollDto payrollDto) {
        Set<Long> idsOfGroupRewardLines = payrollDto.getPayrollRewardLines().stream()
                .map(dto -> {
                    return dto.getRewardGroup().getId();
                })
                .collect(Collectors.toSet());

        List<GroupRewardDto> groupRewards = groupRewardService.getGroupAllowanceEntityByIds(idsOfGroupRewardLines);

        return groupRewards.stream().map(CommonDtoMapper::toCommonDto).collect(Collectors.toList());
    }

}
