package com.nhnacademy.security.config;

import com.nhnacademy.security.auth.CustomLoginSuccessHandler;
import com.nhnacademy.security.repository.MemberRepository;
import com.nhnacademy.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    /* TODO #1: 실습 - 비공개 프로젝트 URL은 (`/private-project/**`) ADMIN 이나 MEMBER 권한이 있을 때 접근 가능하도록 설정해주세요. */
                    .requestMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                    .requestMatchers("/public-project/**").authenticated()
                    .requestMatchers("/profile").authenticated()
                    .requestMatchers("/redirect-index").authenticated()
                    .anyRequest().permitAll()
                    .and();

        http.formLogin()
            .loginPage("/auth/login")
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginProcessingUrl("/login")
                .successHandler(new CustomLoginSuccessHandler());

        http.logout()
            .logoutUrl("/auth/logout")
            .invalidateHttpSession(true)
            .deleteCookies("SESSION");

        http.csrf().disable();

        /* TODO #2: 실습 - Security HTTP Response header 의 기본값을 해제하고 `X-Frame-Options` 헤더의 값을 SAMEORIGIN으로 설정해주세요. */
        http.headers()
            .defaultsDisabled()
            .frameOptions()
                .sameOrigin();

        /* TODO #7: 실습 - custom 403 에러 페이지(`/error/403`)를 설정해주세요. */
        http.exceptionHandling()
                .accessDeniedPage("/error/403");

        return http.build();
    }

    @Bean
    public CustomUserDetailsService userDetailsService(MemberRepository memberRepository) {
        return new CustomUserDetailsService(memberRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
