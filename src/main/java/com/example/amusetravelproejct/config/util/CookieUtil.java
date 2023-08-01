package com.example.amusetravelproejct.config.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;

@Slf4j
public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        log.info("");
        log.info("CookieUtill 에서 getCookie 실행");
        log.info(name + "이라는 쿠키를 찾을려고 합니다.");
        Cookie[] cookies = request.getCookies();
        log.info("cookies : " + cookies);

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
//                log.info("지금 존재하는 쿠키의 이름은 : " + cookie.getName());
                if (name.equals(cookie.getName())) {
                    log.info("지금 찾은 쿠키의 이름은 : " + cookie.getName());
                    log.info("cookie maxAge : " + cookie.getMaxAge());
                    log.info("cookie를 찾는 데 성공했습니다.");
                    log.info("");
                    return Optional.of(cookie);
                }
            }
        }
        log.info("cookie를 찾는 데 실패했습니다.");
        log.info("");
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response,String name, String value, int maxAge) {
        log.info("\n\nCookieUtil 에서 addCookie 진입");
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

//        log.info("cookie : ");
//        log.info(cookie.getValue());
//        log.info(cookie.getName());
//        log.info(cookie.getComment());
//        log.info(cookie.getPath());
//        log.info("domain : " + cookie.getDomain());
//        log.info(String.valueOf(cookie.getMaxAge()));
//        log.info(String.valueOf(cookie.getSecure()));
//        log.info(String.valueOf(cookie.getVersion()));

    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge,String domain) {
        log.info("\n\nCookieUtil 에서 setCookie 진입");


        String[] parts = domain.split("\\.");
        int len = parts.length;
        if (len >= 2) {
            // 마지막 두 개의 요소를 연결하여 도메인을 구성
            domain =  parts[len - 2] + "." + parts[len - 1];
//            domain = "." + domain;
        }
        log.info("domain" + ": " + domain);

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .domain(domain)
                .sameSite("None")
                .httpOnly(false)
                .secure(false)
                .maxAge(maxAge)
                .build();
        response.addHeader("Set-Cookie",cookie.toString());
    }


        public static void addCookie(HttpServletResponse response, String name, String value, int maxAge,String domain) {
        log.info("");
        log.info("CookieUtil 에서 addCookie 진입");

        String[] parts = domain.split("\\.");
        int len = parts.length;
        if (len >= 2) {
            // 마지막 두 개의 요소를 연결하여 도메인을 구성
            domain =  parts[len - 2] + "." + parts[len - 1];
        }

        log.info("domain" + ": " + domain);

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setSecure(false);
        response.addCookie(cookie);

        log.info("cookie 값 : "+cookie.getValue());
        log.info("cookie 이름 : " + cookie.getName());
        log.info("cookie 내용 : "+cookie.getComment());
        log.info("cookie path : " + cookie.getPath());
        log.info("domain : " + cookie.getDomain());
        log.info("cookie maxAge : "+String.valueOf(cookie.getMaxAge()));
        log.info("cookie secure : "+String.valueOf(cookie.getSecure()));
        log.info("cookie version : "+String.valueOf(cookie.getVersion()));
        log.info("");


    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        log.info("");
        log.info("CookieUtil 에서 deleteCookie");

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
//                log.info("지금 존재하는 쿠키의 이름은 : " + cookie.getName());
                if (name.equals(cookie.getName())) {
                    log.info("지금 삭제되는 쿠키의 이름은 : " + cookie.getName());
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        getCookie(request,name);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name,String domain) {
        log.info("\n\nCookieUtil 에서 deleteCookie");

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
//                log.info("지금 존재하는 쿠키의 이름은 : " + cookie.getName());
                if (name.equals(cookie.getName())) {
                    log.info("지금 삭제되는 쿠키의 이름은 : " + cookie.getName());
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setDomain(domain);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        getCookie(request,name);
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
