package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.dto.request.AuthRequest;
import com.example.amusetravelproejct.dto.response.AuthResponse;
import com.example.amusetravelproejct.repository.AdminRepository;
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

    @Transactional
    public ResponseTemplate<String> changePassword(Admin findAdmin, AuthRequest.changePassword request) {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String db_password = encoder.encode(request.getPassword_for_change());

        findAdmin.setPassword(db_password);
        adminRepository.save(findAdmin);
        return new ResponseTemplate("비밀 번호 변경 완료");
    }
}
