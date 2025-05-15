package com.apus.demo.controller;

import com.apus.demo.dto.GroupRewardDto;
import com.apus.demo.dto.GroupRewardSearchCriteria;
import com.apus.demo.dto.request.GroupRewardSearchRequest;
import com.apus.demo.dto.response.BaseResponse;
import com.apus.demo.dto.response.PageResponse;
import com.apus.demo.service.GroupRewardService;
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
@RequestMapping("/group-reward")
@RequiredArgsConstructor
@Slf4j(topic = "GROUP_REWARD_CONTROLLER")
@Validated
@Tag(name = "GROUP REWARD", description = "APIs for managing group rewards")
public class GroupRewardController {

    private final GroupRewardService groupRewardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create a new group reward")
    public BaseResponse<Object> createGroupReward(@Valid @RequestBody GroupRewardDto groupRewardDto) {

        log.info("Creating group reward: {}", groupRewardDto);

        Object object =  groupRewardService.addGroupReward(groupRewardDto);

        return BaseResponse.success(object);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get a group reward by ID")
    public BaseResponse<GroupRewardDto> getGroupReward(@RequestParam Long id) {
        log.info("Fetching group reward with id: {}", id);

        return BaseResponse.success(groupRewardService.getGroupReward(id));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", summary = "Get all group rewards with pagination")
    public BaseResponse<PageResponse<GroupRewardDto>> searchGroupRewards(@ParameterObject GroupRewardSearchRequest request) {

        log.info("Get all or searching group rewards with criteria: {}", request);

        GroupRewardSearchCriteria criteria = request.toGroupRewardSearchCriteria();

        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 20 : request.getSize();
        String sortBy = request.getSort() == null ? "id" : request.getSort();
        String sortDirection = request.getSortDirection() == null ? "DESC" : request.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Page<GroupRewardDto> groupRewardPage = groupRewardService.searchGroupRewards(
                criteria,
                PageRequest.of(page, size, sort)
        );

        return BaseResponse.success(PageResponse.from(groupRewardPage));
    }

    @PutMapping
    @Operation(method = "PUT", summary = "Update a group reward")
    public BaseResponse<Object> updateGroupReward(@Valid @RequestBody GroupRewardDto groupRewardDto) {

        log.info("Updating group reward with id: {}", groupRewardDto.getId());

        Object object = groupRewardService.updateGroupReward(groupRewardDto);

        return BaseResponse.success(object);
    }

    @DeleteMapping
    @Operation(method = "DELETE", summary = "Delete a group reward")
    public BaseResponse<Void> deleteGroupReward(@RequestParam Long id) {

        log.info("Deleting group reward with id: {}", id);

        groupRewardService.deleteGroupReward(id);

        return BaseResponse.success(null);
    }
}
