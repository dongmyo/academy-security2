package com.nhnacademy.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.stream.Stream;

@Controller
public class LogoutController {
    // TODO #8: 실습 - 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = Stream.of(request.getCookies())
                              .filter(c -> "SESSION".equals(c.getName()))
                              .findFirst().orElse(null);

        if (Objects.nonNull(cookie)) {
            cookie.setValue("");
            cookie.setMaxAge(0);

            response.addCookie(cookie);
        }

        return "redirect:/login";
    }

}
