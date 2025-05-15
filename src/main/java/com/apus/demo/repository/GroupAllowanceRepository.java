package com.apus.demo.repository;

import com.apus.demo.entity.GroupAllowanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAllowanceRepository extends JpaRepository<GroupAllowanceEntity, Long>, JpaSpecificationExecutor<GroupAllowanceEntity> {

}
