package com.nhnacademy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO #2: 웹 요청 ACL 스프링 표현식 적용
//        http
//                .authorizeHttpRequests()
//                    .requestMatchers("/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
//                    .requestMatchers("/public-project/**").authenticated()
//                    .anyRequest().permitAll()
//                    .and();

        // login
        http.formLogin();
        // logout
        http.logout();

        // disable csrf
        http.csrf().disable();

        return http.build();
    }

}
