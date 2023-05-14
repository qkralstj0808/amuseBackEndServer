package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.AuthRequest;
import com.example.amusetravelproejct.domain.UserRefreshToken;
import com.example.amusetravelproejct.dto.response.AuthResponse;
import com.example.amusetravelproejct.repository.UserRefreshTokenRepository;
import com.example.amusetravelproejct.social.common.ApiResponse;
import com.example.amusetravelproejct.config.properties.AppProperties;
import com.example.amusetravelproejct.social.oauth.entity.RoleType;
import com.example.amusetravelproejct.social.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.social.oauth.token.AuthToken;
import com.example.amusetravelproejct.social.oauth.token.AuthTokenProvider;
import com.example.amusetravelproejct.config.util.CookieUtil;
import com.example.amusetravelproejct.config.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.example.amusetravelproejct.social.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/login")
    public ApiResponse login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthRequest authRequest
    ) {
        System.out.println("\n\nAuthController에서 /login api 진입");
        /*
            - 사용자 인증 처리
            1. 입력받은 아이디와 비번을 매개변수로 담아서 usernamepasswordAuthenticationToken 객체를 생성한다.
            2. 인증을 시도
            3. 인증 성공하면 Authentication 객체 생성
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getId(),
                        authRequest.getPassword()
                )
        );

        /*
            인증에 성공했으면
            해당 아이디를 SecurityContextHolder를 통해 전역적으로 관리한다.
            이렇게 함으로써 애플리케이션 내에서 로그인한 사용자의 정보를 언제든지 접근하고 활용할 수 있게 됩니다.
            또한, 이 정보는 필요한 경우 Spring Security에서 제공하는 SecurityExpression 등을 이용하여 인가 처리에 활용될 수도 있습니다.
         */
        String userId = authRequest.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);


        Date now = new Date();


        /*
            jwt 기반의 access token을 생성한다.

            - access token 안에 들어갈 정보
            - userid, role (admin, guest, user), expire (만료기간)
         */
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        /*
            refresh token 생성
         */
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        // 확인해서 만약 위에 만들어놓은 refresh 토큰으로 교체한다. 없으면 위에 토큰으로 db에 저장한다.
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        /*
            쿠키 안에 담아 놓은 refresh token을 없애고 새로 생성한 refresh token을 쿠키에 넣는다.
         */
        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }

    @GetMapping("/refresh")
    public ResponseTemplate<AuthResponse.getNewAccessToken> refreshToken (HttpServletRequest request, HttpServletResponse response){

        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        // token이 유효한지 확인
        if (!token.validate()) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        // expired access token 인지 확인
        if(token.getExpiredTokenClaims() == null){
            throw new CustomException(ErrorCode.NOT_EXPIRED_TOKEN);
        }

        Claims claims = token.getExpiredTokenClaims();

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        String db_refreshToken = userRefreshToken.getRefreshToken();
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(db_refreshToken);

//        log.info("userRefreshToken : " + userRefreshToken);

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

        return new ResponseTemplate(new AuthResponse.getNewAccessToken(newAccessToken));
    }


//    @GetMapping("/refresh")
//    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("\n\nAuthController에서 refreshToken 메서드 진입");
//        // access token 확인
//
//        /*
//            request header 중 AUTHENTICATION 키에 대한 값을 가지고 온다. 해당 값이 access token이다.
//         */
//
//        String tokenStr = HeaderUtil.getAccessToken(request);
//        AuthToken token = tokenProvider.convertAuthToken(tokenStr);
//
//        System.out.println(token);
//
//        if (!token.validate()) {
//            return ApiResponse.invalidAccessToken();
//        }
//
////         expired access token 인지 확인
//        Claims claims = token.getExpiredTokenClaims();
//        if (claims == null) {
//
//            return ApiResponse.notExpiredTokenYet();
//        }
//
//        System.out.println("이제 UserId 찾기 시작");
////
//        String userId = claims.getSubject();
//        RoleType roleType = RoleType.of(claims.get("role", String.class));
//
////         refresh token
//        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
//                .map(Cookie::getValue)
//                .orElse((null));
//        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);
//
//        if (!authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
////         userId refresh token 으로 DB 확인
//        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
//
//        if (userRefreshToken == null) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        Date now = new Date();
//        AuthToken newAccessToken = tokenProvider.createAuthToken(
//                userId,
//                roleType.getCode(),
//                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
//        );
//
//        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
//
//        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
//        if (validTime <= THREE_DAYS_MSEC) {
//            // refresh 토큰 설정
//            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
//
//            authRefreshToken = tokenProvider.createAuthToken(
//                    appProperties.getAuth().getTokenSecret(),
//                    new Date(now.getTime() + refreshTokenExpiry)
//            );
//
//            // DB에 refresh 토큰 업데이트
//            userRefreshToken.setRefreshToken(authRefreshToken.getToken());
//
//            int cookieMaxAge = (int) refreshTokenExpiry / 60;
//            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
//        }
//
//        return ApiResponse.success("token", newAccessToken.getToken());
//    }

}
