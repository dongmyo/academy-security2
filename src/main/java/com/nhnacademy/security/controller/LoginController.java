package com.nhnacademy.security.controller;

import com.nhnacademy.security.exception.LoginFailureException;
import com.nhnacademy.security.model.MemberLoginRequest;
import com.nhnacademy.security.model.MemberResponse;
import com.nhnacademy.security.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@Controller
//@RequestMapping("/login")
public class LoginController {
    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public ModelAndView processLogin(MemberLoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws LoginFailureException {
        MemberResponse memberResponse = memberService.processLogin(loginRequest);

        HttpSession session = request.getSession();
        session.setAttribute("loginId", memberResponse.getId());

        Cookie cookie = new Cookie("SESSION", session.getId());
        response.addCookie(cookie);

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("loginName", memberResponse.getName());

        return mav;
    }

}
