package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;



    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

}