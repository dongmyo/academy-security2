package com.nhnacademy.security.config;

import com.nhnacademy.security.auth.CustomLoginSuccessHandler;
import com.nhnacademy.security.repository.MemberRepository;
import com.nhnacademy.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers("/teacher/**").hasAuthority("ROLE_TEACHER")
                    .requestMatchers("/student/**").hasAuthority("ROLE_STUDENT")
                    .requestMatchers("/redirect-index").authenticated()
                    .anyRequest().permitAll()
                    .and();

        http.formLogin()
            /* TODO #6: 실습 - 로그인 페이지 커스터마이징 */
            /* ... */
            .successHandler(new CustomLoginSuccessHandler());

        http.logout()
            /* TODO #8: 실습 - 로그아웃 페이지 커스터마이징 */
            /* ... */
            .and();

        http.csrf().disable();

        http.exceptionHandling()
                .accessDeniedPage("/error/403");

        return http.build();
    }

    /* TODO #9: 실습 - CustomUserDetailsService 를 반환하는 Bean 을 등록하세요 */
    /* ... */

    // TODO #10: PasswordEncoder - NoOpPasswordEncoder.
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
