package com.nhnacademy.security.exception;

public class LoginFailureException extends Exception {
    public LoginFailureException(String name) {
        super("login failed: name=" + name);
    }

}
