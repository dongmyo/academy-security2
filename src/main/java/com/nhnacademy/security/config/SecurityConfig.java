package com.nhnacademy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// TODO #1: security config
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .anyRequest().authenticated()
                    .and();

        http.formLogin();

        return http
                .httpBasic(Customizer.withDefaults())
                .headers(h -> {
                    h.cacheControl().disable();
                    h.contentTypeOptions().disable();
                })
                .build();
    }

}
