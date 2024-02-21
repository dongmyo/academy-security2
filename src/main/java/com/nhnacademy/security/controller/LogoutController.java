package com.nhnacademy.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {
    // TODO #8: 실습 - 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
