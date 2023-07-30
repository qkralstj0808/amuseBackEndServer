package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.AdminRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Admin createAdmin(String adminId, String password){
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setPassword(password);

        adminRepository.save(admin);
        return admin;
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
}