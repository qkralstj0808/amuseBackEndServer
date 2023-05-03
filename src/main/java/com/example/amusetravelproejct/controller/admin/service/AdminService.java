package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.AdminAdvertisement;
import com.example.amusetravelproejct.domain.SupervisorInfo;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Admin admin) {
        Optional<Admin> existingAdmin = adminRepository.findById(admin.getId());
        if (existingAdmin.isPresent()) {
            return adminRepository.save(admin);
        } else {
            throw new ResourceNotFoundException("Admin not found with id " + admin.getId());
        }
    }

    public void deleteAdmin(Long id) {
        Optional<Admin> existingAdmin = adminRepository.findById(id);
        if (existingAdmin.isPresent()) {
            adminRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Admin not found with id " + id);
        }
    }

    public List<SupervisorInfo> getAllSupervisorInfosByAdminId(Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isPresent()) {
            return admin.get().getSupervisorInfos();
        } else {
            throw new ResourceNotFoundException("Admin not found with id " + adminId);
        }
    }

    public List<AdminAdvertisement> getAllAdminAdvertisementsByAdminId(Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isPresent()) {
            return admin.get().getAdminAdvertisements();
        } else {
            throw new ResourceNotFoundException("Admin not found with id " + adminId);
        }
    }

//    public Optional<Admin> getAdminByEmailAndPassword(String email, String password) {
//        return adminRepository.findByEmailAndPassword(email, password);
//    }
}