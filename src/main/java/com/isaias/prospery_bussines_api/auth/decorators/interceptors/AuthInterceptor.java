package com.isaias.prospery_bussines_api.auth.decorators.interceptors;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.isaias.prospery_bussines_api.auth.decorators.Auth;
import com.isaias.prospery_bussines_api.auth.jwt.JwtService;
import com.isaias.prospery_bussines_api.user.accessor.UserAccessor;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserAccessor userAccessor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod method)) return true;
        
        Auth auth = method.getMethodAnnotation(Auth.class);
        if (auth == null) auth = method.getBeanType().getAnnotation(Auth.class);
        if (auth == null) return true; 
        
        String token = extractToken(request);
        if (token == null || !jwtService.isValid(token)) 
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        
        String uuid = jwtService.extractUuid(token);
        UserEntity user = userAccessor.getUserById(uuid);
        if(!user.isActive()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not active");

        request.setAttribute("User", user);

        boolean hasRole = Arrays.asList(auth.value()).contains(String.valueOf(user.getRole()));
        if (!hasRole) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Insufficient permissions");
        return true;
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
    }
}


