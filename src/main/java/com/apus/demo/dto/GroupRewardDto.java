package com.apus.demo.dto;

import com.apus.demo.entity.IdentifiableEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupRewardDto implements IdentifiableEntity {

    private Long id;

    @NotBlank(message = "code must be not blank")
    private String code;

    @NotBlank(message = "name must be not blank")
    @Size(min = 3, max = 255)
    private String name;

    private CommonDto parent;

    private String description;

    @NotNull(message = "isActive must be not null")
    private Boolean isActive;

}
