package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.UserRefreshToken;
import com.example.amusetravelproejct.oauth.token.AuthToken;
import com.example.amusetravelproejct.repository.UserRefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserRefreshTokenService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Transactional
    public void saveRefreshToken(String id, AuthToken refreshToken){
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(id);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(id, refreshToken.getToken());
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        userRefreshTokenRepository.save(userRefreshToken);
    }

    @Transactional
    public void deleteRefreshToken(String id) {
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(id);
        // refreshToken 삭제
        if (userRefreshToken != null) {
            userRefreshTokenRepository.delete(userRefreshToken);
        }
    }

}
