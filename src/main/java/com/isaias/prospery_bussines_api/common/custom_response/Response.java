package com.isaias.prospery_bussines_api.common.custom_response;

public interface Response <T> {
    public abstract T toJson();
    public abstract int getCode();
}
