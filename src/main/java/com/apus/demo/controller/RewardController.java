package com.apus.demo.controller;

import com.apus.demo.dto.*;
import com.apus.demo.dto.request.CommonSearchRequest;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import com.apus.demo.service.RewardService;
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
@RequestMapping("/reward")
@RequiredArgsConstructor
@Slf4j(topic = "REWARD_CONTROLLER")
@Validated
@Tag(name = "REWARD", description = "APIs for managing reward")
public class RewardController {

    private final RewardService rewardService;
    private final MessageUtil messageUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create a new reward")
    public BaseResponse<CommonDto> createReward(@Valid @RequestBody RewardDto rewardDto) {
        log.info("Creating reward: {}", rewardDto);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                rewardService.createReward(rewardDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get a reward by ID")
    public BaseResponse<RewardDto> getReward(@RequestParam Long id) {
        log.info("Fetching reward with id: {}", id);

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                rewardService.getReward(id));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all rewards with pagination")
    public BaseResponse<PageResponse<RewardListDto>> getListRewards(@ParameterObject CommonSearchRequest request) {
        log.info("Get all or searching rewards with criteria: {}", request);

        CommonSearchCriteria criteria = request.toCommonSearchCriteria();

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 20 : request.getSize();
        String sortBy = request.getSort() == null ? "id" : request.getSort();
        String sortDirection = request.getSortDirection() == null ? "DESC" : request.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Page<RewardListDto> rewardPage = rewardService.getListRewards(criteria, PageRequest.of(page, size, sort));

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                PageResponse.from(rewardPage));
    }

    @PutMapping
    @Operation(method = "PUT", summary = "Update a reward by ID")
    public BaseResponse<CommonDto> updateReward(@Valid @RequestBody RewardDto rewardDto) {
        log.info("Updating reward with id: {}", rewardDto.getId());

        return BaseResponse.success(messageUtil.getMessage("response.success"),
                rewardService.updateReward(rewardDto));
    }

    @DeleteMapping
    @Operation(method = "DELETE", summary = "Delete a reward by ID")
    public BaseResponse<Void> deleteReward(@RequestParam Long id) {
        log.info("Deleting reward with id: {}", id);

        rewardService.deleteReward(id);

        return BaseResponse.success(messageUtil.getMessage("response.success"), null);
    }
}
