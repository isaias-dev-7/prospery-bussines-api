package com.isaias.prospery_bussines_api.user.dtos;

import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPassDto {
    @NotBlank
    @Size(min = 8, message = UserMessages.INVALID_PASSWORD)
    private String password;

    @NotBlank
    @Size(min = 6)
    private String code;
}

