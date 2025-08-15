package com.isaias.prospery_bussines_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.isaias.prospery_bussines_api.auth.decorators.interceptors.AuthInterceptor;
import com.isaias.prospery_bussines_api.auth.decorators.resolvers.GetUserArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private GetUserArgumentResolver getUserArgumentResolver;
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(getUserArgumentResolver);
    }

     @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }
}

