package com.nhnacademy.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/student/**").setViewName("student");
        registry.addViewController("/teacher/**").setViewName("teacher");
        registry.addViewController("/error/403").setViewName("error403");

        /* TODO #4: URL 확인 */
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("logout");

        registry.addRedirectViewController("/redirect-index", "/");
    }

}
