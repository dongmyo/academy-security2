package com.nhnacademy.security.config;

import com.nhnacademy.security.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RedisTemplate<String, Object> redisTemplate;

    public WebConfig(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/admin/**").setViewName("admin");
        registry.addViewController("/private-project/**").setViewName("private-project");
        registry.addViewController("/public-project/**").setViewName("public-project");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/auth/login").setViewName("login");
        registry.addViewController("/auth/logout").setViewName("logout");
        registry.addRedirectViewController("/redirect-index", "/");
        registry.addViewController("/error/403").setViewName("error403");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor(redisTemplate));
    }

}
