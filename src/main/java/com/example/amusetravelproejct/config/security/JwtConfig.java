package com.example.amusetravelproejct.config.security;

import com.example.amusetravelproejct.oauth.token.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtConfig {
    @Value("${spring.jwt.secret}")
    private String secret;

    private final UserDetailsService userDetailService;

    public JwtConfig(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(secret, userDetailService);
    }
}
