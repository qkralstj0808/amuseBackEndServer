package com.example.amusetravelproejct.oauth.service;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.oauth.exception.OAuthProviderMissMatchException;
import com.example.amusetravelproejct.oauth.info.OAuth2UserInfoFactory;
import com.example.amusetravelproejct.repository.UserRepository;
import com.example.amusetravelproejct.oauth.entity.ProviderType;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.oauth.info.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // 소셜 로그인이 성공하면 해당 메서드가 바로 실행이 된다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            log.info("\n\nCustomOauth2UserService에서 loadUser 함수가 실행이 되었다.");
            log.info(String.valueOf(userRequest.getClientRegistration()));
            log.info(String.valueOf(userRequest.getAccessToken()));
            log.info(userRequest.getAdditionalParameters().toString());

            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        log.info("\n\nCustomOAuth2UserService에서 process 메서드에 진입했습니다.");
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        log.info("userInfo.getId() : " + userInfo.getId());

        User savedUser = userRepository.findByUserId(userInfo.getId());

        // 데이터베이스에 저장되어 있는 user라면?
        if (savedUser != null) {

            log.info("데이터베이스에 있는 유저입니다.");
            // 카카오로 회원가입 먼저 했는데 구글로 로그인할려고 한다면?
            if (providerType != savedUser.getProviderType()) {
                log.info("다른 계정으로 먼저 로그인이 되었습니다.");
                log.info( "Looks like you're signed up with " + providerType +
                        " account. Please use your " + savedUser.getProviderType() + " account to login.");

                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                        " account. Please use your " + savedUser.getProviderType() + " account to login."
                );
            }

            updateUser(savedUser, userInfo);
        } else {
            log.info("CustomOAuth2UserService에 process 메서드 안에 있다.");
            log.info("데이터베이스에 없는 유저라서 create한다.");
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getEmail(),
                "Y",
                userInfo.getImageUrl(),
                providerType,
                RoleType.USER,
                now,
                now
        );

        return userRepository.saveAndFlush(user);
    }

    // 만약에 카카오톡에서 자신의 이미지나 이름을 바꿨으면 우리 사이트에서도 적용이 되도록 업데이트 시킨다.
    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
            user.setUsername(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
            user.setProfileImageUrl(userInfo.getImageUrl());
        }

        return user;
    }
}
