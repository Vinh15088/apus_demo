package com.apus.demo.repository;

import com.apus.demo.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, Long>, JpaSpecificationExecutor<RewardEntity> {

}
