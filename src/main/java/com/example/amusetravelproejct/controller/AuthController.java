package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.dto.request.AuthRequest;
import com.example.amusetravelproejct.domain.UserRefreshToken;
import com.example.amusetravelproejct.dto.response.AuthResponse;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.UserRefreshTokenRepository;
import com.example.amusetravelproejct.config.properties.AppProperties;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.oauth.token.AuthToken;
import com.example.amusetravelproejct.oauth.token.AuthTokenProvider;
import com.example.amusetravelproejct.config.util.CookieUtil;
import com.example.amusetravelproejct.config.util.HeaderUtil;
import com.example.amusetravelproejct.repository.UserRepository;
import com.example.amusetravelproejct.service.AdminService;
import com.example.amusetravelproejct.service.AuthService;
import com.example.amusetravelproejct.service.UserRefreshTokenService;
import com.example.amusetravelproejct.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    private final AdminRepository adminRepository;
    private final AdminService adminService;

    private final UserRefreshTokenService userRefreshTokenService;

    private final static long THREE_DAYS_MSEC = 259200000;

    private final static int COOKIE_MAX_AGE = 3600;
    private final static String REFRESH_TOKEN = "refresh_token";
    private final static String REDIRECT_URL = "target_url";
    private final static String ACCESS_TOKEN = "__jwtk__";
    private final RedirectStrategy redirectStrategy;

    private final long ADMIN_ACCESS_TOKEN_EXPIRE = 3600000 * 3;
    private final long USER_ACCESS_TOKEN_EXPIRE = 3600000;

    @CrossOrigin(originPatterns = "*", allowCredentials = "true")
    @GetMapping("/token/success")
    public void getTokenSuccess(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Optional<Cookie> access_token_cookie = CookieUtil.getCookie(request, "access_token");
        Optional<Cookie> redirect_uri_cookie = CookieUtil.getCookie(request, REDIRECT_URL);

        if(access_token_cookie.isEmpty()){
            log.info("access_token_cookie가 비었습니다.");
            throw new CustomException(ErrorCode.EMPTY);
        }else{
            log.info("access_token_cookie는 있습니다.");
            log.info("access_token : " + access_token_cookie.get().getValue());
        }

        log.info(String.valueOf(redirect_uri_cookie.isEmpty()));

        if(redirect_uri_cookie.isEmpty()){
            throw new CustomException(ErrorCode.EMPTY);
        }else{
            log.info("redirect_uri_cookie 있습니다.");
            log.info("redirect_uri_cookie : " + redirect_uri_cookie.get().getValue());
        }

        String access_token = access_token_cookie.get().getValue();
        String target_url = redirect_uri_cookie.get().getValue();

        log.info("access_token : " + access_token_cookie.get().getValue());
        log.info("redirect_uri : " + redirect_uri_cookie.get().getValue());


        String domain = target_url;

        URL parsedUrl = null;
        try {
            parsedUrl = new URL(domain);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        domain = parsedUrl.getHost();
        String path = parsedUrl.getPath();

        String[] parts = domain.split("\\.");
        int len = parts.length;
        if (len >= 2) {
            // 마지막 두 개의 요소를 연결하여 도메인을 구성
            domain =  parts[len - 2] + "." + parts[len - 1];
        }
        log.info("domain" + ": " + domain);
        CookieUtil.addCookie(response,"__jwtk__",access_token,60,request.getServerName());
        CookieUtil.addCookie(response,"__jwtk__",access_token,60,domain);

        CookieUtil.deleteCookie(request,response,"access_token",domain);
        CookieUtil.deleteCookie(request,response,REDIRECT_URL,domain);

        log.info(request.toString());

        redirectStrategy.sendRedirect(request,response,target_url);
//        return new ResponseTemplate(new AuthResponse.getAccessToken_targetUrl(access_token_cookie.get().getValue()));
    }

    @GetMapping("/getCookie/access-token")
    public ResponseEntity<AuthResponse.getAccessToken> getCookie(HttpServletRequest request){
//                            @CookieValue(value = "access_token") Cookie cookie) {
        log.info("/getCookie/access-token 실행");
        log.info(request.toString());
        Optional<Cookie> access_token_optional = CookieUtil.getCookie(request, "__jwtk__");
        log.info(access_token_optional.toString());

        if(!access_token_optional.isEmpty()){
            log.info(access_token_optional.get().getValue());
        }else{
            throw new CustomException(ErrorCode.EMPTY);
        }

        return new ResponseEntity<>(new AuthResponse.getAccessToken(access_token_optional.get().getValue()),HttpStatus.OK);
    }

    @GetMapping("/delete/cookie/access-token")
    public ResponseTemplate<String> deleteCookie(HttpServletRequest request,HttpServletResponse response){
//                            @CookieValue(value = "access_token") Cookie cookie) {

        if(!CookieUtil.getCookie(request,"__jwtk__").isEmpty()){
            CookieUtil.deleteCookie(request,response,"__jwtk__");
        }else{
            throw new CustomException(ErrorCode.EMPTY);
        }

        return new ResponseTemplate<>("쿠키 삭제 성공");
    }


    @GetMapping("/token/fail")
    public ResponseTemplate<AuthResponse.getError> getTokeFailed(HttpServletRequest request,
                                                                  HttpServletResponse response, @RequestParam("error") String errorMessage){
        return new ResponseTemplate(new AuthResponse.getError(errorMessage));
    }


    @PostMapping("/signup")
    public ResponseTemplate<AuthResponse.getAccessToken> adminSignup(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthRequest.Id_Password authRequest
    ) {
        System.out.println("\n\nAuthController에서 /login api 진입");

        String adminId = authRequest.getId();
        String password = authRequest.getPassword();

        Optional<Admin> adminId_optional = adminRepository.findByAdminId(adminId);

        // db에 없으면 새로 생성
        if(adminId_optional.isEmpty()){
            log.info("db에 없음");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String db_password = encoder.encode(password);
            adminService.createAdmin(adminId, db_password);
        }else{
            throw new CustomException(ErrorCode.ID_EXIST);
        }

        Authentication authentication =  processAuth(adminId, password);

        // access token 생성
        AuthToken accessToken = createAccessToken(adminId,authentication);

        // refresh token 생성
        AuthToken refreshToken = createRefreshToken();

        // userId refresh token 으로 DB 확인
        // 확인해서 만약 있으면 위에 만들어놓은 refresh 토큰으로 교체한다. 없으면 위에 토큰으로 db에 저장한다.
        userRefreshTokenService.saveRefreshToken(adminId,refreshToken);

        /*
            쿠키 안에 담아 놓은 refresh token을 없애고 새로 생성한 refresh token을 쿠키에 넣는다.
         */
        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response,ACCESS_TOKEN,accessToken.getToken(),cookieMaxAge);

        return new ResponseTemplate(new AuthResponse.getAccessToken(accessToken.getToken()));
//        return null;
    }



    @GetMapping("/login")
    public ResponseTemplate<AuthResponse.getAccessToken> adminLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "id",required = true) String adminId,
            @RequestParam(value = "password",required = true) String password
    ) {

        Optional<Admin> adminId_optional = adminRepository.findByAdminId(adminId);

        // db에 없으면 새로 생성
        if(adminId_optional.isEmpty()){
            throw new CustomException(ErrorCode.ID_NOT_EXIST);
        }

        Authentication authentication =  processAuth(adminId, password);

        // access token 생성
        AuthToken accessToken = createAccessToken(adminId,authentication);

        // refresh token 생성
        AuthToken refreshToken = createRefreshToken();

        // userId refresh token 으로 DB 확인
        // 확인해서 만약 있으면 위에 만들어놓은 refresh 토큰으로 교체한다. 없으면 위에 토큰으로 db에 저장한다.
        userRefreshTokenService.saveRefreshToken(adminId,refreshToken);


        // 쿠키 안에 담아 놓은 refresh token을 없애고 새로 생성한 refresh token을 쿠키에 넣는다.
        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response,ACCESS_TOKEN,accessToken.getToken(),cookieMaxAge);

        return new ResponseTemplate(new AuthResponse.getAccessToken(accessToken.getToken()));
//        return null;
    }

    @PostMapping("/password/change")
    public ResponseTemplate<String> changeAdminPassword(
            @RequestBody AuthRequest.changePassword authRequest
    ){
        Optional<Admin> adminByAdminId = adminService.getAdminByAdminId(authRequest.getId());
        if(adminByAdminId.isEmpty()){
            throw new CustomException(ErrorCode.ID_NOT_EXIST);
        }

        Admin findAdmin = adminByAdminId.get();

        return adminService.changeAdminPassword(findAdmin,authRequest);
    }

    @DeleteMapping("/admin/withdraw")
    public ResponseTemplate<String> AdminWithdraw(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "id",required = true) String adminId
    ){
        Optional<Admin> adminByAdminId = adminService.getAdminByAdminId(adminId);
        if(adminByAdminId.isEmpty()){
            throw new CustomException(ErrorCode.ID_NOT_EXIST);
        }
        Admin findAdmin = adminByAdminId.get();

        // refreshToken 삭제
        userRefreshTokenService.deleteRefreshToken(findAdmin.getAdminId());

        // admin 삭제
        adminService.deleteAdmin(findAdmin);

        return new ResponseTemplate("삭제 성공");

    }

    @PostMapping("/user/signup")
    public ResponseTemplate<AuthResponse.getAccessToken> userSignup(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthRequest.Id_Password authRequest
    ) {

        String userId = authRequest.getId();
        String password = authRequest.getPassword();

        User user = userRepository.findByUserId(userId);

        // db에 없으면 새로 생성
        if(user == null){
            log.info("db에 없음");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String db_password = encoder.encode(password);
            user = userService.createUser(userId, db_password);
        }else{
            throw new CustomException(ErrorCode.ID_EXIST);
        }

        Authentication authentication =  processAuth(userId, password);

        // access token 생성
        AuthToken accessToken = createAccessToken(userId,authentication);

        // refresh token 생성
        AuthToken refreshToken = createRefreshToken();

        // userId refresh token 으로 DB 확인
        // 확인해서 만약 있으면 위에 만들어놓은 refresh 토큰으로 교체한다. 없으면 위에 토큰으로 db에 저장한다.
        userRefreshTokenService.saveRefreshToken(userId,refreshToken);

        /*
            쿠키 안에 담아 놓은 refresh token을 없애고 새로 생성한 refresh token을 쿠키에 넣는다.
         */
        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response,ACCESS_TOKEN,accessToken.getToken(),cookieMaxAge);

        return new ResponseTemplate(new AuthResponse.getAccessToken(accessToken.getToken()));
//        return null;
    }

    @GetMapping("/user/login")
    public ResponseTemplate<AuthResponse.getAccessToken> userLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "id",required = true) String userId,
            @RequestParam(value = "password",required = true) String password
    ) {


        User findUser = userRepository.findByUserId(userId);

        // db에 없으면 에러
        if(findUser == null){
            throw new CustomException(ErrorCode.ID_NOT_EXIST);
        }

        Authentication authentication =  processAuth(userId, password);

        // access token 생성
        AuthToken accessToken = createAccessToken(userId,authentication);

        // refresh token 생성
        AuthToken refreshToken = createRefreshToken();

        // userId refresh token 으로 DB 확인
        // 확인해서 만약 위에 만들어놓은 refresh 토큰으로 교체한다. 없으면 위에 토큰으로 db에 저장한다.
        userRefreshTokenService.saveRefreshToken(userId,refreshToken);

        /*
            쿠키 안에 담아 놓은 refresh token을 없애고 새로 생성한 refresh token을 쿠키에 넣는다.
         */
        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response,ACCESS_TOKEN,accessToken.getToken(),cookieMaxAge);

        return new ResponseTemplate(new AuthResponse.getAccessToken(accessToken.getToken()));
//        return null;
    }

    @PostMapping("/user/password/change")
    public ResponseTemplate<String> changeUserPassword(
            @RequestBody AuthRequest.changePassword authRequest
    ){
        User findUser = userRepository.findByUserId(authRequest.getId());
        if(findUser == null){
            throw new CustomException(ErrorCode.ID_NOT_EXIST);
        }

        return userService.changeUserPassword(findUser,authRequest);
    }

    @GetMapping("/refresh")
    public ResponseTemplate<AuthResponse.getNewAccessToken> refreshToken (HttpServletRequest request, HttpServletResponse response){

        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        // token이 유효한지 확인
        if (!token.validate()) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
//
//        // expired access token 인지 확인 expired token이 아니면 그냥 반환
        if(token.getExpiredTokenClaims() == null){
            return new ResponseTemplate(new AuthResponse.getNewAccessToken(token));
        }
//
//        Claims claims = token.getExpiredTokenClaims();


        Claims tokenClaims = token.getTokenClaims();
        String userId = tokenClaims.getSubject();
        Grade grade;

        // admin일 때
        if(tokenClaims.get("grade") == null){
            grade = null;
        }else{      // user 일 때
            grade = Grade.valueOf(tokenClaims.get("grade").toString());
        }

        RoleType roleType = RoleType.of(tokenClaims.get("role", String.class));

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);

        if(userRefreshToken == null){
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
        String db_refreshToken = userRefreshToken.getRefreshToken();
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(db_refreshToken);

        Date now = new Date();
        AuthToken newAccessToken = null;
        if(roleType.equals(RoleType.ADMIN)){
            newAccessToken = tokenProvider.createAuthToken(
                    userId,
                    roleType.getCode(),
//                user == null ? null : user.getGrade() ,
                    grade,
                    new Date(now.getTime() + ADMIN_ACCESS_TOKEN_EXPIRE)
            );
        }else{
            newAccessToken = tokenProvider.createAuthToken(
                    userId,
                    roleType.getCode(),
//                user == null ? null : user.getGrade() ,
                    grade,
                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
            );
        }


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

    private Authentication processAuth(String id, String password ){
//        - 사용자 인증 처리
//        1. 입력받은 아이디와 비번을 매개변수로 담아서 usernamepasswordAuthenticationToken 객체를 생성한다.
//        2. 인증을 시도
//        3. 인증 성공하면 Authentication 객체 생성

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        id,
                        password
                )
        );

        /*
            인증에 성공했으면
            해당 아이디를 SecurityContextHolder를 통해 전역적으로 관리한다.
            이렇게 함으로써 애플리케이션 내에서 로그인한 사용자의 정보를 언제든지 접근하고 활용할 수 있게 됩니다.
            또한, 이 정보는 필요한 경우 Spring Security에서 제공하는 SecurityExpression 등을 이용하여 인가 처리에 활용될 수도 있습니다.
         */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    private AuthToken createAccessToken(String id,Authentication authentication ){
        Date now = new Date();

        AuthToken accessToken = tokenProvider.createAuthToken(
                id,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                Grade.Bronze,
                new Date(now.getTime() + USER_ACCESS_TOKEN_EXPIRE)
        );

        log.info("accessToken : " + accessToken);

        return accessToken;

    }

    private AuthToken createRefreshToken(){
        Date now = new Date();

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        log.info("refreshToken : " + refreshToken);

        return refreshToken;
    }


}
