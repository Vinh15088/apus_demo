package com.apus.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommonDto {
    private Long id;
    private String code;
    private String name;
}
