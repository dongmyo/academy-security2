package com.nhnacademy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    // TODO #1: InMemoryUserDetailsManager
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .build();

        UserDetails member = User.withUsername("member")
                                 .password("{noop}member")
                                 .authorities("ROLE_MEMBER")
                                 .build();

        UserDetails guest = User.withUsername("guest")
                                 .password("{noop}guest")
                                 .authorities("ROLE_GUEST")
                                 .build();

        return new InMemoryUserDetailsManager(admin, member, guest);
    }

}
