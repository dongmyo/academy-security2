package com.nhnacademy.security.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// TODO #6: `/logout` 페이지에서 로그아웃을 구현합니다.
@Controller
public class LogoutController {
    private final RedisTemplate<String, Object> sessionRedisTemplate;

    public LogoutController(RedisTemplate<String, Object> sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }

    @GetMapping("/logout")
    public String logout() {
        // TODO #6-1: 실습 - `SESSION` 쿠키를 삭제합니다.

        // TODO #6-2: 실습 - redis 에서 session 정보를 삭제합니다.

        // TODO #6-3: 실습 - `/login` 페이지로 redirect 합니다.
        return null;
    }

}
