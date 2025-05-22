package com.apus.demo.entity;

import com.apus.demo.util.enums.AllowanceRewardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reward")
public class RewardEntity extends AbstractEntity<Long> implements IdentifiableEntity {

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "name", length = 55, nullable = false)
    private String name;

    @Column(name = "include_type", length = 55)
    private String includeType;

    @Column(name = "type", length = 55)
    @Enumerated(EnumType.STRING)
    private AllowanceRewardType type;

    private Long uomId;

    private Long currencyId;

    private Long groupRewardId;

    @Column(name = "description" )
    private String description;

    @Column(name = "is_active" , length = 55)
    private Boolean isActive;

}
