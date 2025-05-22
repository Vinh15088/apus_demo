package com.apus.demo.dto;

import com.apus.demo.dto.validator.EnumValue;
import com.apus.demo.entity.IdentifiableEntity;
import com.apus.demo.util.enums.AllowanceRewardType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowanceDto implements IdentifiableEntity {
    private Long id;

    @NotBlank(message = "code must be not blank")
    private String code;

    @NotBlank(message = "name must be not blank")
    private String name;

    private CommonDto groupAllowance;

    @NotNull(message = "includeType must be not null")
    private Set<String> includeType;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = AllowanceRewardType.class)
    private String type;

    @NotNull(message = "uom must be not null")
    private CommonDto uom;

    @NotNull(message = "currency must be not null")
    private CommonDto currency;

    private String description;

    @NotNull(message = "isActive must be not null")
    private Boolean isActive;
}
