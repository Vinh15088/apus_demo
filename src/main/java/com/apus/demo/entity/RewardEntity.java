package com.apus.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RewardEntity extends AbstractEntity<Long> {

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "name", length = 55, nullable = false)
    private String name;

    @Column(name = "include_type", length = 55)
    private String includeType;

    @Column(name = "type", length = 55)
    private String type;

    private Long uomId;

    private Long currencyId;

    private Long groupAllowanceId;

    @Column(name = "description" )
    private String description;

    @Column(name = "status" , length = 55)
    private String status;
}
