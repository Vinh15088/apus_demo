package com.apus.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RewardListDto {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String isActive;
}
