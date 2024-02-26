package com.nhnacademy.security.config;

import com.nhnacademy.security.auth.CustomLoginSuccessHandler;
import com.nhnacademy.security.repository.MemberRepository;
import com.nhnacademy.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
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

        // TODO : #4 oauth2Login()
        http.oauth2Login()
            .clientRegistrationRepository(clientRegistrationRepository())
            .authorizedClientService(authorizedClientService())
            .and();

//        http.formLogin()
//            .loginPage("/auth/login")
//                .usernameParameter("id")
//                .passwordParameter("pwd")
//                .loginProcessingUrl("/login")
//                .successHandler(new CustomLoginSuccessHandler());

        http.logout()
            .logoutUrl("/auth/logout")
            .invalidateHttpSession(true)
            .deleteCookies("SESSION");

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

    // TODO : #2 ClientRegistrationRepository with ClientRegistration.
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(ClientRegistration.withRegistrationId("naver")
                                                                          .clientId("i1uKug9bdiBnP3FLed03")
                                                                          .clientSecret("4RkRczMtEY")
                                                                          .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                                                          .scope("name", "email", "profile_image")
                                                                          .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                                                                          .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                                                                          .tokenUri("https://nid.naver.com/oauth2.0/token")
                                                                          .userInfoUri("https://openapi.naver.com/v1/nid/me")
                                                                          .userNameAttributeName("response")
                                                                          .build());
    }

    // TODO : #3 OAuth2AuthorizedClientService
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

}
