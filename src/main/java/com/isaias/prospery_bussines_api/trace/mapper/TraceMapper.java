package com.isaias.prospery_bussines_api.trace.mapper;

import com.isaias.prospery_bussines_api.trace.entity.TraceEntity;
import com.isaias.prospery_bussines_api.trace.record.TraceRecord;

public class TraceMapper {
    public static TraceEntity toEntity(TraceRecord record) {
            TraceEntity trace = new TraceEntity();
            trace.setType(record.type());
            trace.setTableName(record.tableName());
            trace.setDescription(record.description());
            trace.setUser(record.user());
            return trace;
    }
}
