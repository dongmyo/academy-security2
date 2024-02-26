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
                // TODO #4: login success handler 설정
                .successHandler(loginSuccessHandler(null));

        http.logout()
            .logoutUrl("/auth/logout")
            .invalidateHttpSession(true)
            .deleteCookies("SESSION")
            // TODO #7: logout success handler 설정
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

    // TODO #3: login success handler bean
    @Bean
    public CustomLoginSuccessHandler loginSuccessHandler(RedisTemplate<Object, Object> redisTemplate) {
        return new CustomLoginSuccessHandler(redisTemplate);
    }

    // TODO #6: logout success handler bean
    @Bean
    public CustomLogoutSuccessHandler logoutSuccessHandler(RedisTemplate<Object, Object> redisTemplate) {
        return new CustomLogoutSuccessHandler(redisTemplate);
    }

}
