package com.nhnacademy.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/student/**").setViewName("student");
        registry.addViewController("/teacher/**").setViewName("teacher");
        registry.addViewController("/error/403").setViewName("error403");

        registry.addRedirectViewController("/redirect-index", "/");
    }

}
