package com.nhnacademy.security.controller;

import com.nhnacademy.security.exception.LoginFailureException;
import com.nhnacademy.security.model.MemberLoginRequest;
import com.nhnacademy.security.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    // TODO #6: 로그인 폼
    @GetMapping
    public String login() {
        return "login";
    }

    // TODO #7: 실습 - 로그인 처리
    @PostMapping
    public ModelAndView processLogin(MemberLoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws LoginFailureException {
        return null;
    }

}
