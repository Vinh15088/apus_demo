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
public class GroupRewardEntity extends AbstractEntity<Long> {

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "name", length = 55, nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}