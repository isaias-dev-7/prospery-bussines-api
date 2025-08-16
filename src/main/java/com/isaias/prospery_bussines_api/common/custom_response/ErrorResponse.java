package com.isaias.prospery_bussines_api.common.custom_response;

import java.util.Map;

public class ErrorResponse extends RuntimeException implements Response<Map<String, Object>> {
    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public Map<String, Object> toJson() {
        return Map.of("data", Map.of("message", this.message));
    }

    @Override
    public int getCode() {
        return this.statusCode;
    }

    public static ErrorResponse build(int statusCode, String message) {
        return new ErrorResponse(statusCode, message);
    }
}
