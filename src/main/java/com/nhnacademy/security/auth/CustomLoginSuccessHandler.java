package com.nhnacademy.security.auth;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO #2: 실습 - redis template 을 sessionRedisTemplate 을 사용하도록 변경하시오.
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final RedisTemplate<Object, Object> redisTemplate;


    public CustomLoginSuccessHandler(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String sessionId = UUID.randomUUID().toString();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());

        redisTemplate.opsForHash().put(sessionId, "username", userDetails.getUsername());
        redisTemplate.opsForHash().put(sessionId, "authority", authorities.get(0).getAuthority());

        Cookie cookie = new Cookie("SESSION", sessionId);
        cookie.setMaxAge(259200);     // 3일

        response.addCookie(cookie);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
