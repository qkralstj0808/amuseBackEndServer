package com.example.amusetravelproejct.config.security;

import com.example.amusetravelproejct.config.properties.CorsProperties;
//import com.example.amusetravelproejct.config.resTemplate.CustomAccessDeniedHandler;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.oauth.handler.CustomAuthenticationFailureHandler;
import com.example.amusetravelproejct.repository.UserRefreshTokenRepository;
import com.example.amusetravelproejct.config.properties.AppProperties;
import com.example.amusetravelproejct.oauth.exception.RestAuthenticationEntryPoint;
import com.example.amusetravelproejct.oauth.filter.TokenAuthenticationFilter;
import com.example.amusetravelproejct.oauth.filter.TokenExceptionFilter;
import com.example.amusetravelproejct.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.example.amusetravelproejct.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.example.amusetravelproejct.oauth.handler.TokenAccessDeniedHandler;
import com.example.amusetravelproejct.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.amusetravelproejct.oauth.service.CustomOAuth2UserService;
import com.example.amusetravelproejct.oauth.service.CustomUserDetailsService;
import com.example.amusetravelproejct.oauth.token.AuthTokenProvider;
import com.example.amusetravelproejct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final TokenExceptionFilter tokenExceptionFilter;

    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

//    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    private final UserRepository userRepository;


    /*
    * UserDetailsService 설정
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())

                .and()

                    // 권한을 줄 수 있다.
                    .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                    .antMatchers("/api/**/users/**").hasAnyAuthority(RoleType.USER.getCode())
                    .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                    .anyRequest().permitAll()   // 위에 언급한 url 말고 나머지는 authenticated 된 사용자만 이용할 수 있도록 한다.

                .and()
                    .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                    .redirectionEndpoint()
                    .baseUri("/*/oauth2/code/*")
                .and()
                    .userInfoEndpoint()
                    .userService(oAuth2UserService)
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler())
                    .failureHandler(oAuth2AuthenticationFailureHandler())
                .and()
                    .exceptionHandling()
                .accessDeniedHandler(tokenAccessDeniedHandler);


        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenExceptionFilter, TokenAuthenticationFilter.class);
    }

    /*
    * auth 매니저 설정
    * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
    * security 설정 시, 사용할 인코더 설정
    * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    * 토큰 필터 설정
    * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(appProperties,
                tokenProvider,
                userRefreshTokenRepository);
    }

    /*
    * 쿠키 기반 인가 Repository
    * 인가 응답을 연계 하고 검증할 때 사용.
    * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
    * Oauth 인증 성공 핸들러
    * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userRepository
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    /*
    * Cors 설정
    * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        System.out.println("SecurityConfig에서 corsConfigurationSource 진입");
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.addAllowedOrigin("http://amusetravel.wheelgo.net/");
        corsConfig.addAllowedOrigin("https://myadmin.wheelgo.net/");
        corsConfig.addAllowedOrigin("http://localhost:3000");
        corsConfig.addAllowedOrigin("http://localhost:3001");
//        corsConfig.addAllowedOriginPattern("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.setAllowCredentials(true);
//        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
