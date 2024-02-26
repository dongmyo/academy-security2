package com.nhnacademy.security.auth;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

// TODO #4: 실습 - login success handler를 구현하세요.
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final RedisTemplate<String, Object> sessionRedisTemplate;


    public CustomLoginSuccessHandler(RedisTemplate<String, Object> sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String sessionId = UUID.randomUUID().toString();

        // TODO #4-1: `SESSION` 이라는 이름의 쿠키에 sessionId를 저장하세요.

        // TODO #4-2: redis에 session 정보를 저장하세요.
        String username = null;
        String authority = null;

        sessionRedisTemplate.opsForHash().put(sessionId, "username", username);
        sessionRedisTemplate.opsForHash().put(sessionId, "authority", authority);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
