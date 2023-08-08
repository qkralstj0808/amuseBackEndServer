package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.TargetUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetUserRepository extends JpaRepository<TargetUser, Long> {

    List<TargetUser> findByUserId(Long user_id);
}
