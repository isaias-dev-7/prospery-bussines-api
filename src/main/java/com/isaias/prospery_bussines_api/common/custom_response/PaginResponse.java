package com.isaias.prospery_bussines_api.common.custom_response;

import java.util.Map;


public class PaginResponse implements Response<Map<String, Object>> {
    private int statusCode;
    private int total;
    private int page;
    private int limit;
    private int totalPage;
    private Object data;

    public PaginResponse(
            int statusCode,
            Object data,
            int total,
            int page,
            int limit,
            int totalPage) {
        this.statusCode = statusCode;
        this.data = data;
        this.total = total;
        this.page = page;
        this.limit = limit;
        this.totalPage = totalPage;
    }

    @Override
    public Map<String, Object> toJson() {
        return Map.ofEntries(
                Map.entry("total", this.total),
                Map.entry("page", this.page),
                Map.entry("limit", this.limit),
                Map.entry("totalPage", this.totalPage),
                Map.entry("data", this.data));
    }

    @Override
    public int getCode() {
        return this.statusCode;
    }

    public static PaginResponse build(
            int statusCode,
            Object data,
            int total,
            int page,
            int limit,
            int totalPage) {
        return new PaginResponse(statusCode, data, total, page, limit, totalPage);
    }

}
