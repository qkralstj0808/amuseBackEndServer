package com.example.amusetravelproejct.oauth.handler;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.domain.UserRefreshToken;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.amusetravelproejct.repository.UserRefreshTokenRepository;
import com.example.amusetravelproejct.config.properties.AppProperties;
import com.example.amusetravelproejct.oauth.entity.ProviderType;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.oauth.info.OAuth2UserInfo;
import com.example.amusetravelproejct.oauth.info.OAuth2UserInfoFactory;
import com.example.amusetravelproejct.oauth.token.AuthToken;
import com.example.amusetravelproejct.oauth.token.AuthTokenProvider;
import com.example.amusetravelproejct.config.util.CookieUtil;
import com.example.amusetravelproejct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("\n\nOAuth2AuthenticationSuccessHandler 에서 onAuthenticationSuccess");
        log.info("authentication.getAuthorities().toString() : " + authentication.getAuthorities().toString());
        String targetUrl = determineTargetUrl(request, response, authentication);
//        String accessToken = determineTargetUrl(request,response,authentication);

//        if (response.isCommitted()) {
//            log.debug("response가 committed 되어서 해당 redirect로 못감 : " + accessToken);
//            return;
//        }

//        String targetUrl =  UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("token", accessToken)
//                .build().toUriString();

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Transactional
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("\n\nOAuth2AuthenticationSuccessHandler 에서 determineTargetUrl");
        log.info("authentication : " + authentication);

        Optional<String> redirectUri = CookieUtil.getCookie(request, OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        log.info("redirectUri : " + redirectUri);

        log.info("redirect uri : " + redirectUri);
        log.info("redirectUri.isPresent() : " + redirectUri.isPresent());
        log.info("!isAuthorizedRedirectUri(redirectUri.get() : " + !isAuthorizedRedirectUri(redirectUri.get()));

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = authToken.getPrincipal().getAttributes();
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = ((OidcUser) authentication.getPrincipal());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        Collection<? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();

        RoleType roleType = hasAuthority(authorities, RoleType.ADMIN.getCode()) ? RoleType.ADMIN : RoleType.USER;
        log.info("roleType : " + roleType);
        Date now = new Date();

        log.info("현재 authToken은" + String.valueOf(authToken));
        log.info("proverType : " + providerType);
        String user_id = null;

        if(providerType.toString().equals("KAKAO")){
            user_id = attributes.get("id").toString();
        }else if(providerType.toString().equals("GOOGLE")){
            user_id = attributes.get("sub").toString();
        }else if(providerType.toString().equals("NAVER")){
            Map<String, Object> naver_attributes = (Map<String, Object>)attributes.get("response");
            user_id = naver_attributes.get("id").toString();
        }

        User findUser = userRepository.findByUserId(user_id);

        AuthToken accessToken = null;

        if(findUser == null){
            accessToken = tokenProvider.createAuthToken(
                    userInfo.getId(),
                    roleType.getCode(),
                    Grade.BRONZE,
                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

        }else{
            accessToken = tokenProvider.createAuthToken(
                    userInfo.getId(),
                    roleType.getCode(),
                    findUser.getGrade(),
                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));
        }


        log.info("accesstoken : " + accessToken.getToken());
        log.info("accesstoken : " + accessToken.getTokenClaims().get("grade"));

        // refresh 토큰 설정
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );
        log.info("refreshToken : " + refreshToken.getToken());

        // refresh token db에 저장.
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userInfo.getId());

        if (userRefreshToken != null) {
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        } else {
            userRefreshToken = new UserRefreshToken(userInfo.getId(), refreshToken.getToken());
        }

        userRefreshTokenRepository.saveAndFlush(userRefreshToken);

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(request, response, OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN);
        CookieUtil.addCookie(response, OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken.getToken())
                .build().toUriString();
//        return accessToken.getToken();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        log.info("\n\nOAuth2AuthenticationSuccessHandler 에서 clearAuthenticationAttributes");
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        log.info("\n\nOAuth2AuthenticationSuccessHandler 에서 hasAuthority");
        if (authorities == null) {
            return false;
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            log.info("grantedAuthority : " + grantedAuthority);
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        log.info("\n\nOAuth2AuthenticationSuccessHandler 에서 isAuthorizedRedirectUri 진입");
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    log.info("authrozedURI : " + authorizedURI);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
