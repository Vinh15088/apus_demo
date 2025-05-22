package com.apus.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowancePolicyListDto {

    private Long id;
    private String name;
    private String code;
    private String description;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String state;
}
