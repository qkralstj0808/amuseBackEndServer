package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.UserRefreshToken;
import com.example.amusetravelproejct.dto.request.AuthRequest;
import com.example.amusetravelproejct.dto.response.AuthResponse;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.UserRefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AdminRepository adminRepository;

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Transactional
    public ResponseTemplate<String> changePassword(Admin findAdmin, AuthRequest.changePassword request) {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String db_password = encoder.encode(request.getPassword_for_change());

        findAdmin.setPassword(db_password);
        adminRepository.save(findAdmin);
        return new ResponseTemplate("비밀 번호 변경 완료");
    }

    @Transactional
    public void withdraw(Admin admin) {

        // refreshToken 삭제

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(admin.getAdminId());
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshTokenRepository.delete(userRefreshToken);
        }

        // admin 테이블에서 삭제
        adminRepository.delete(admin);
    }
}
