package com.nhnacademy.security.auth;

import com.nhnacademy.security.util.CookieUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

// TODO #3: 실습 - redis template 을 sessionRedisTemplate 을 사용하도록 변경하시오.
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final RedisTemplate<String, Object> sessionRedisTemplate;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomLogoutSuccessHandler(RedisTemplate<String, Object> sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String sessionId = CookieUtils.getCookieValue(request, "SESSION");
        if (Objects.nonNull(sessionId)) {
            sessionRedisTemplate.opsForHash().delete(sessionId, "username", "authority");
        }

        redirectStrategy.sendRedirect(request, response, "/");
    }

}
