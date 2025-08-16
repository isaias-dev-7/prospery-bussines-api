package com.isaias.prospery_bussines_api.common.custom_response;

public interface Response <T> {
    public T toJson();
    public int getCode();
}
