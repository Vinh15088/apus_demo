package com.apus.demo.repository;

import com.apus.demo.entity.GroupRewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRewardRepository extends JpaRepository<GroupRewardEntity, Long>,
        JpaSpecificationExecutor<GroupRewardEntity> {

}
