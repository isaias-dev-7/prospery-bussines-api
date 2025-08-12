package com.isaias.prospery_bussines_api.common.custom_response;

import java.util.Map;

public class SuccessResponse implements Response<Map<String, Object>>{
    private int statusCode;
    private Object data;

    public SuccessResponse(int statusCode, Object data){
        this.statusCode = statusCode;
        this.data = data;
    }

    @Override
    public Map<String, Object> toJson() {
        return Map.of("data", this.data);
    }

    @Override
    public int getCode() {
        return this.statusCode;
    }
    
    public static SuccessResponse build(int statusCode, Object data){
        return new SuccessResponse(statusCode, data);
    }
}
