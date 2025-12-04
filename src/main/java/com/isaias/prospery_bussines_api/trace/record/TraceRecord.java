package com.isaias.prospery_bussines_api.trace.record;

import com.isaias.prospery_bussines_api.trace.enums.TraceEnum;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

public record TraceRecord(
    TraceEnum type,
    String description,
    String tableName,
    UserEntity user
) {}
