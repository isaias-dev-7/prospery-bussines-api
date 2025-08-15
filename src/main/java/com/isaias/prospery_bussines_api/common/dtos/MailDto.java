package com.isaias.prospery_bussines_api.common.dtos;

import lombok.Data;

@Data
public class MailDto {
    private String message;
    private String to;
    private String subject;
}
