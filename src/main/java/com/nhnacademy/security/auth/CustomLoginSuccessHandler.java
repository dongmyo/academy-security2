package com.nhnacademy.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// TODO #7: login success handler - 세션 만들고, 쿠키 생성한 다음 메인 페이지로 리다이렉트.
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();

        Cookie cookie = new Cookie("LOGIN", session.getId());
        response.addCookie(cookie);

        response.sendRedirect("/");
    }

}
