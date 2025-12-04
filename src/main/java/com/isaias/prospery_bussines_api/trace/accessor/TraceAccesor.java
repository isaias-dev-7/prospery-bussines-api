package com.isaias.prospery_bussines_api.trace.accessor;

import org.springframework.stereotype.Component;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.trace.entity.TraceEntity;
import com.isaias.prospery_bussines_api.trace.mapper.TraceMapper;
import com.isaias.prospery_bussines_api.trace.record.TraceRecord;
import com.isaias.prospery_bussines_api.trace.repository.TraceRepository;

@Component
public class TraceAccesor {
    private final TraceRepository traceRepository;
    private final UtilsService utilsService;

    public TraceAccesor(
        TraceRepository traceRepository, 
        UtilsService utilsService
    ) {
        this.traceRepository = traceRepository;
        this.utilsService = utilsService;
    }

    public void createTrace(TraceRecord record){
        try {
            TraceEntity trace = TraceMapper.toEntity(record);
            traceRepository.save(trace);
        } catch (Exception e) {
            throw handleException(e, "createTrace");
        }
    }
    
    private ErrorResponse handleException(Throwable error, String function) {
        System.out.println("[ERROR] -  /trace/accessor/TraceAccessor: " + function);
        throw utilsService.handleError(error);
    }
}
