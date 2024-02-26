package com.nhnacademy.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CookieUtils {
    private CookieUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        return Arrays.stream(cookies)
                     .filter(c -> c.getName().equals(cookieName))
                     .map(Cookie::getValue)
                     .findFirst()
                     .orElse(null);
    }

}
