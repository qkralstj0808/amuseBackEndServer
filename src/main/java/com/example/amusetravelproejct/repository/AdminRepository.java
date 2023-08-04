package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

public interface AdminRepository extends JpaRepository<Admin, Long> {
//    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByAdminId(String admin_id);
    //    Admin findByUserId(String userId);
}
