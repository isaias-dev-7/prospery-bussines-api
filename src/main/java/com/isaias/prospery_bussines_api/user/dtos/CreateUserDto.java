package com.isaias.prospery_bussines_api.user.dtos;

import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = UserMessages.INVALID_PHONE)
    private String phone;

    @NotBlank
    @Size(min = 8, message = UserMessages.INVALID_PASSWORD)
    private String password;

    @NotBlank
    @Pattern(regexp = "^(USER|ADMIN|SELLER)$", message = UserMessages.INVALID_ROLE)
    private String role;
}
