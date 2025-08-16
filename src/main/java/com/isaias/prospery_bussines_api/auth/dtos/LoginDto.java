package com.isaias.prospery_bussines_api.auth.dtos;

import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$",
        message = UserMessages.INVALID_PASSWORD
    )
    private String password;
}
