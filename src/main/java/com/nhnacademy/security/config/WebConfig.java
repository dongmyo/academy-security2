package com.nhnacademy.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/admin/**").setViewName("admin");
        registry.addViewController("/private-project/**").setViewName("private-project");
        registry.addViewController("/public-project/**").setViewName("public-project");
        registry.addViewController("/profile").setViewName("profile");
        // TODO #5: 로그인 페이지 view controller 설정.
        registry.addViewController("/auth/login").setViewName("login");
        // TODO #6: 로그아웃 페이지 view controller 설정.
        registry.addViewController("/auth/logout").setViewName("logout");
    }

}
