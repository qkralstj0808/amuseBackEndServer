package com.example.amusetravelproejct.social.utils;

import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;

public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        System.out.println("CookieUtill 에서 getCookie 실행");
        Cookie[] cookies = request.getCookies();
        System.out.println("cookies : " + cookies);

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        System.out.println("\n\nCookieUtil 에서 addCookie 진입");
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);

        System.out.println("cookie : ");
        System.out.println(cookie.getValue());
        System.out.println(cookie.getName());
        System.out.println(cookie.getComment());
        System.out.println(cookie.getPath());
        System.out.println(cookie.getDomain());
        System.out.println(cookie.getMaxAge());
        System.out.println(cookie.getSecure());
        System.out.println(cookie.getVersion());
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        System.out.println("\n\nCookieUtil 에서 deleteCookie");

        Cookie[] cookies = request.getCookies();
        System.out.println("cookies : " + cookies);


        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }

}
