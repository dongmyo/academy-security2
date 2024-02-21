package com.nhnacademy.security.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler(LoginFailureException.class)
    public ModelAndView handleLoginFailureException(LoginFailureException exception) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", exception.getMessage());

        return mav;
    }

}
