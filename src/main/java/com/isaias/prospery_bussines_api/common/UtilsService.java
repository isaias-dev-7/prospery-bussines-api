package com.isaias.prospery_bussines_api.common;

import java.util.function.Supplier;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.custom_response.Response;
import com.isaias.prospery_bussines_api.common.messages_response.CommonMesajes;

@Service
public class UtilsService {
    private final BCryptPasswordEncoder passwordEncoder;

    public UtilsService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String hashPassword(String password) {
        if (password == null || password.trim().isEmpty())
            throw new IllegalArgumentException(CommonMesajes.INVALID_PARAM);

        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    public ResponseEntity<?> handleResponse(Supplier<Response<?>> supplier) {
        Response<?> res = null;
        try {
            res = supplier.get();
        } catch (Exception e) {
            res = this.handleError(e);
        }

        return ResponseEntity.status(res.getCode()).body(res.toJson());
    }

    public ErrorResponse handleError(Throwable error) {
        if (error instanceof ErrorResponse)
            return (ErrorResponse) error;
        return new ErrorResponse(400, error.getMessage());
    }
}
