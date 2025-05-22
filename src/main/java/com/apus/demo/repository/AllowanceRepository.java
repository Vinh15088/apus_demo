package com.apus.demo.repository;

import com.apus.demo.entity.AllowanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowanceRepository extends JpaRepository<AllowanceEntity, Long>,
        JpaSpecificationExecutor<AllowanceEntity> {

}
