package com.example.amusetravelproejct.social.oauth.filter;

import com.example.amusetravelproejct.config.properties.AppProperties;
import com.example.amusetravelproejct.domain.UserRefreshToken;
import com.example.amusetravelproejct.social.api.repository.user.UserRefreshTokenRepository;
import com.example.amusetravelproejct.social.common.ApiResponse;
import com.example.amusetravelproejct.social.oauth.entity.RoleType;
import com.example.amusetravelproejct.social.oauth.token.AuthToken;
import com.example.amusetravelproejct.social.oauth.token.AuthTokenProvider;
import com.example.amusetravelproejct.social.utils.CookieUtil;
import com.example.amusetravelproejct.social.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.example.amusetravelproejct.social.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

//        System.out.println("\n\nTokenAuthenticationFilter에서 doFilterInternal 진입");
        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

//        Cookie[] cookies = request.getCookies();
//        if(cookies != null){
//            for(Cookie cookie:cookies){
//                OAuth2AuthorizationRequest deserialize = CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
////                System.out.println("getName : " + cookie.getName());
////                System.out.println("getValue : " + cookie.getValue());
////                System.out.println("getComment : " + cookie.getComment());
////                System.out.println(deserialize.getAuthorizationUri());
////                System.out.println(deserialize.getState());
////                System.out.println(deserialize.getScopes());
////                System.out.println(deserialize.getAuthorizationUri());
//            }
//        }

        if (token.validate()) {
            if(token.getExpiredTokenClaims() != null){
                // expired 된 토큰이면 refresh token을 통해 재발급한다.
//                System.out.println("expired된 토큰이니까 refresh token으로 다시 access token 발급");
                token = reGetAccessToken(request, response, token);
            }

//            System.out.println("else문 안에 들어옴");
////            System.out.println("token.validate() : " + token.validate());
            Authentication authentication = tokenProvider.getAuthentication(token);
//            System.out.println("authentication : " + authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private AuthToken reGetAccessToken(HttpServletRequest request, HttpServletResponse response, AuthToken expired_access_token){

        // expired access token 인지 확인
        Claims claims = expired_access_token.getExpiredTokenClaims();

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

//        System.out.println("userId : " + userId);
//        System.out.println("roleType : " + roleType);

        // refresh token
//        String refreshToken = to
//        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

//
//        if (authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
//        }

////        System.out.println("refreshToken : " + refreshToken);
////        System.out.println("userId : " + userId);


        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        String db_refreshToken = userRefreshToken.getRefreshToken();
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(db_refreshToken);

//        System.out.println("userRefreshToken : " + userRefreshToken);

        if (userRefreshToken == null) {
            return null;
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }



        return newAccessToken;
    }


}
