package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);

//    Admin findByUserId(String userId);
}
