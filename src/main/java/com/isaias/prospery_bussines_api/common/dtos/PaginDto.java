package com.isaias.prospery_bussines_api.common.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PaginDto {
    @Min(value = 0)
    private int page = 0;

    @Min(value = 1)
    private int limit = 10;

    private String searchTerm = "";

    private String role = "";
}
