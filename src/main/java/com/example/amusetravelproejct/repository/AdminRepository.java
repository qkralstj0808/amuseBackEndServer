package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUserId(String user_id);
    //    Admin findByUserId(String userId);
}
