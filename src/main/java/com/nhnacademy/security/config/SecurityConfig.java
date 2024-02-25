package com.nhnacademy.security.config;

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

        // login
        http.formLogin();
        // logout
        http.logout();

        // disable csrf
        http.csrf().disable();

        return http.build();
    }

    // TODO #1: UserDetailService 빈 등록.
    @Bean
    public CustomUserDetailsService userDetailsService(MemberRepository memberRepository) {
        return new CustomUserDetailsService(memberRepository);
    }

    // TODO #2: PasswordEncoder 빈 등록.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
