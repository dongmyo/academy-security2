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
                    .requestMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                    .requestMatchers("/public-project/**").authenticated()
                    .requestMatchers("/profile").authenticated()
                    .anyRequest().permitAll()
                    .and();

        http.formLogin()
            .loginPage("/auth/login")
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginProcessingUrl("/login")
                // TODO #2: login success handler 설정.
                .successHandler(new CustomLoginSuccessHandler());

        // TODO #7: 실습 - logout 커스터마이즈: 로그아웃했을 때 SESSION 이라는 이름의 쿠키를 지우고 세션을 invalidate 시켜주세요.
        http.logout()
            .logoutUrl("/auth/logout")
            .invalidateHttpSession(true)
            .deleteCookies("SESSION");

        // TODO #1: CSRF 끄기.
        http.csrf().disable();

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
