package com.isaias.prospery_bussines_api.trace.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.isaias.prospery_bussines_api.trace.entity.TraceEntity;

public interface TraceRepository extends JpaRepository<TraceEntity, UUID>, JpaSpecificationExecutor<TraceEntity> {
    
}
