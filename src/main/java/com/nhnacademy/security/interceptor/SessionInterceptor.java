package com.nhnacademy.security.interceptor;

import com.nhnacademy.security.util.CookieUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

// TODO #4: 실습 - redis template 을 sessionRedisTemplate 을 사용하도록 변경하시오.
public class SessionInterceptor implements HandlerInterceptor {
    private final RedisTemplate<String, Object> sessionRedisTemplate;


    public SessionInterceptor(RedisTemplate<String, Object> sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = CookieUtils.getCookieValue(request, "SESSION");
        if (Objects.nonNull(sessionId)) {
            String username = (String) sessionRedisTemplate.opsForHash().get(sessionId, "username");
            String authority = (String) sessionRedisTemplate.opsForHash().get(sessionId, "authority");

            request.setAttribute("username", username);
            request.setAttribute("authority", authority);
        }

        return true;
    }

}
