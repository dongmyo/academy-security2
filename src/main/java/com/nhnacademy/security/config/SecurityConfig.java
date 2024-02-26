package com.nhnacademy.security.config;

import com.nhnacademy.security.auth.CustomLoginSuccessHandler;
import com.nhnacademy.security.auth.CustomLogoutSuccessHandler;
import com.nhnacademy.security.repository.MemberRepository;
import com.nhnacademy.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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
                    .requestMatchers("/redirect-index").authenticated()
                    .anyRequest().permitAll()
                    .and();

        http.formLogin()
            .loginPage("/auth/login")
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler(null));

        http.logout()
            .logoutUrl("/auth/logout")
            .invalidateHttpSession(true)
            .deleteCookies("SESSION")
            .logoutSuccessHandler(logoutSuccessHandler(null));

        http.csrf().disable();

        http.headers()
            .defaultsDisabled()
            .frameOptions()
                .sameOrigin();

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

    @Bean
    public CustomLoginSuccessHandler loginSuccessHandler(RedisTemplate<String, Object> sessionRedisTemplate) {
        return new CustomLoginSuccessHandler(sessionRedisTemplate);
    }

    @Bean
    public CustomLogoutSuccessHandler logoutSuccessHandler(RedisTemplate<String, Object> sessionRedisTemplate) {
        return new CustomLogoutSuccessHandler(sessionRedisTemplate);
    }

}
