package com.example.amusetravelproejct.config.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/*
 해당 request의 "Authorization" 헤더에서 access token 값을 추출합니다.
 만약 Authorization 헤더가 존재하지 않거나, Bearer 토큰으로 시작하지 않는 경우 null을 반환합니다.
 */

@Slf4j
public class HeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);
        log.info("headerValue : " + headerValue);
        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return headerValue;
    }
}
