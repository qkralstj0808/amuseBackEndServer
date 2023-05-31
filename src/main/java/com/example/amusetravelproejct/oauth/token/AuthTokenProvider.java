package com.example.amusetravelproejct.oauth.token;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.oauth.exception.TokenValidFailedException;
import com.example.amusetravelproejct.repository.UserRepository;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";


    private final UserDetailsService userDetailService;


    private final UserRepository userRepository;

    public AuthTokenProvider(String secret, UserDetailsService userDetailService, UserRepository userRepository) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
    }

    public AuthToken createAuthToken(String id, Date expiry) {
        System.out.println("\n\nAuthTokenProvider 에서 createAuthToken");
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(String id, String role, Grade grade, Date expiry) {
        return new AuthToken(id, role, grade,expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {
        System.out.println("AuthTokenProvider 에서 getAuthentication");
        log.info("doFilter 내에서 getAuthentication : "+authToken.getTokenClaims().get("role"));
        if(authToken.validate()) {
            UserDetails userDetails = null;
//            if(authToken.getTokenClaims().get("role").equals(RoleType.USER)){
//                userDetails = userDetailService.loadUserByUsername(authToken.getTokenClaims().getSubject());
//            }else if(authToken.getTokenClaims().get("role").equals(RoleType.ADMIN)){
//                userDetails =
//            }else{
//                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
//            }

            userDetails = userDetailService.loadUserByUsername(authToken.getTokenClaims().getSubject());

            return new UsernamePasswordAuthenticationToken(userDetails, authToken, userDetails.getAuthorities());
        } else {
            throw new TokenValidFailedException();
        }
    }

}
