package com.nhnacademy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO #2: 실습 - 로그인한 사용자에게는 프로필 페이지(`/profile`) 가 보이도록 설정하세요.
        http
                .authorizeHttpRequests()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                    .requestMatchers("/public-project/**").authenticated()
                    .anyRequest().permitAll()
                    .and();

        // login
        http.formLogin();
        // logout
        http.logout();

        // disable csrf
        http.csrf().disable();

        return http.build();
    }

}
