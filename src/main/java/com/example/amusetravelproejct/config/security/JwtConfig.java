package com.example.amusetravelproejct.config.security;

import com.example.amusetravelproejct.oauth.token.AuthTokenProvider;
import com.example.amusetravelproejct.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtConfig {
    @Value("${spring.jwt.secret}")
    private String secret;

    private final UserDetailsService userDetailService;

    private final UserRepository userRepository;

    public JwtConfig(UserDetailsService userDetailService, UserRepository userRepository) {
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(secret, userDetailService, userRepository);
    }
}
