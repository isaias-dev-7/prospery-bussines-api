package com.isaias.prospery_bussines_api.auth.decorators.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import com.isaias.prospery_bussines_api.auth.decorators.GetUser;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class GetUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GetUser.class)
                && parameter.getParameterType().equals(UserEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        UserEntity user = (UserEntity) request.getAttribute("User");

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found on request");
        }

        return user;
    }

}
