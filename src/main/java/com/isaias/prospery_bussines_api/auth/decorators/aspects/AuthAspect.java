package com.isaias.prospery_bussines_api.auth.decorators.aspects;

import java.util.Arrays;

import javax.management.relation.Role;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.isaias.prospery_bussines_api.auth.decorators.Auth;
import com.isaias.prospery_bussines_api.auth.jwt.JwtService;
import com.isaias.prospery_bussines_api.user.accessor.UserAccessor;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserAccessor userAccessor;

    @Around("@annotation(auth)")
    public Object validate(ProceedingJoinPoint joinPoint, Auth auth) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = extractToken(request);

        if (token == null || !jwtService.isValid(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");

        String uuid = jwtService.extractUuid(token);
        UserEntity user = userAccessor.getUserById(uuid);

        boolean hasRole = Arrays.asList(auth.value()).contains(String.valueOf(user.getRole()));
        if(!hasRole) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Insufficient permissions");
        return joinPoint.proceed();
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
    }
}
