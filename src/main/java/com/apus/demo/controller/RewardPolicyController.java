package com.apus.demo.controller;

import com.apus.demo.dto.*;
import com.apus.demo.dto.request.RewardPolicySearchRequest;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import com.apus.demo.service.RewardPolicyService;
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
@RequestMapping("/reward-policy")
@RequiredArgsConstructor
@Slf4j(topic = "REWARD_POLICY_CONTROLLER")
@Validated
@Tag(name = "REWARD_POLICY", description = "APIs for managing reward policy")
public class RewardPolicyController {

    private final RewardPolicyService rewardPolicyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create a new reward policy")
    public BaseResponse<CommonDto> createRewardPolicy(@Valid @RequestBody RewardPolicyDto rewardPolicyDto) {
        log.info("Creating reward policy");

        return BaseResponse.success(rewardPolicyService.createRewardPolicy(rewardPolicyDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get a reward policy by ID")
    public BaseResponse<RewardPolicyDto> getRewardPolicy(@RequestParam Long id) {
        log.info("Fetching reward policy with id: {}", id);

        return BaseResponse.success(rewardPolicyService.getRewardPolicy(id));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all reward policies with pagination")
    public BaseResponse<PageResponse<RewardPolicyListDto>> getListRewardPolicies(
            @ParameterObject RewardPolicySearchRequest request) {
        log.info("Get all or searching reward policies with criteria: {}", request);

        RewardPolicySearchCriteria criteria = request.toRewardPolicySearchCriteria();

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 20 : request.getSize();
        String sortBy = request.getSort() == null ? "id" : request.getSort();
        String sortDirection = request.getSortDirection() == null ? "DESC" : request.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Page<RewardPolicyListDto> rewardPolicyPage = rewardPolicyService.getListRewardPolicy(criteria, PageRequest.of(page, size, sort));

        return BaseResponse.success(PageResponse.from(rewardPolicyPage));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "PUT", summary = "Update a reward policy by ID")
    public BaseResponse<CommonDto> updateRewardPolicy(@Valid @RequestBody RewardPolicyDto rewardPolicyDto) {
        log.info("Updating reward policy with id: {}", rewardPolicyDto.getId());

        return BaseResponse.success(rewardPolicyService.updateRewardPolicy(rewardPolicyDto));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = "DELETE", summary = "Delete a reward policy by ID")
    public BaseResponse<CommonDto> deleteRewardPolicy(@RequestParam Long id) {
        log.info("Deleting reward policy with id: {}", id);

        rewardPolicyService.deleteRewardPolicy(id);

        return BaseResponse.success(null);
    }
}
