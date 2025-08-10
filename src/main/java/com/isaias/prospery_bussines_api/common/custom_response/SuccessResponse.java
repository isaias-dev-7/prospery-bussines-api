package com.isaias.prospery_bussines_api.common.custom_response;

public class SuccessResponse implements Response<Object>{
    private int statusCode;
    private Object data;

    public SuccessResponse(int statusCode, Object data){
        this.statusCode = statusCode;
        this.data = data;
    }

    @Override
    public Object toJson() {
        return this.data;
    }

    @Override
    public int getCode() {
        return this.statusCode;
    }
    
    public static SuccessResponse build(int statusCode, Object data){
        return new SuccessResponse(statusCode, data);
    }
}
