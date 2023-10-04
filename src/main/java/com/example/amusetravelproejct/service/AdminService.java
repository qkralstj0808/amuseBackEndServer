package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.dto.request.AuthRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    public Optional<Admin> getAdminByAdminId(String admin_id) {
        return adminRepository.findByAdminId(admin_id);
    }

   @Transactional
    public void createAdmin(String adminId, String password){
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setPassword(password);

        adminRepository.save(admin);
    }

    public Admin getAdminPrincipal(UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        String admin_id = userPrincipal.getUserId();

        return adminRepository.findByAdminId(admin_id).get();
    }


    public ResponseTemplate<AdminResponse.getAllAccountsId> getAllAccountsId() {
        List<Admin> adminAllAccounts = adminRepository.findAll();

        return new ResponseTemplate(new AdminResponse.getAllAccountsId(adminAllAccounts.stream().map(
                Admin::getAdminId
        ).collect(Collectors.toList())));
    }

    @Transactional
    public ResponseTemplate<String> changeAdminPassword(Admin findAdmin, AuthRequest.changeAdminPassword request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String db_password = encoder.encode(request.getPassword_for_change());

        findAdmin.setPassword(db_password);
        adminRepository.save(findAdmin);
        return new ResponseTemplate("비밀 번호 변경 완료");
    }

    @Transactional
    public void deleteAdmin(Admin findAdmin){
        adminRepository.delete(findAdmin);
    }
}