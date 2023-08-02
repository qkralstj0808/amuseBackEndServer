package com.example.amusetravelproejct.oauth.repository;

import com.example.amusetravelproejct.config.util.CookieUtil;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public final static String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    public final static String REFRESH_TOKEN = "refresh_token";
    public final static String ACCESS_TOKEN = "__jwtk__";       // access token을 쿠키에 담아서 클라이언트로 보낸다.
    private final static int cookieExpireSeconds = 18000;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        log.info("\n\nOAuth2AuthorizationRequestBasedOnCookieRepository에서 loadAuthorizationRequest 메서드 실행");
//        log.info("cookie(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME) : " + CookieUtil.getCookie(request,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
//                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class)));

//        log.info(CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
//                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
//                .orElse(null).get);
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("OAuth2AuthorizationRequestBasedOnCookieRepository 에서 saveAuthorizationRequest 진입");
        log.info("authorizationRequest : " + authorizationRequest.getAuthorizationRequestUri() + "\n" +authorizationRequest.getAuthorizationUri());

        if (authorizationRequest == null) {
            log.info("authoizationRequest가 Null이면 실행");
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            return;
        }

        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);        // request에서 redirect_uri 부분을 가져온다.
        log.info("cookie 생성 후 redirectUriAfterLogin" + redirectUriAfterLogin);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {            // redirect_uri가 있다면
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        log.info("\n\nOAuth2AuthorizationRequestBasedOnCookieRepository 에서 removeAuthorizationRequest");
        return this.loadAuthorizationRequest(request);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("\n\nOAuth2AuthorizationRequestBasedOnCookieRepository 에서 removeAuthorizationRequest");
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
    }
}
