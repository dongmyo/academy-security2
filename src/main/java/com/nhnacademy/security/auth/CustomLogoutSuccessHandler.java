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

// TODO #8: logout success handler 구현
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final RedisTemplate<Object, Object> redisTemplate;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomLogoutSuccessHandler(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String sessionId = CookieUtils.getCookieValue(request, "SESSION");
        if (Objects.nonNull(sessionId)) {
            redisTemplate.opsForHash().delete(sessionId, "username", "authority");
        }

        redirectStrategy.sendRedirect(request, response, "/");
    }

}
