package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryCustom {
    User findByUserId(String userId);
    Optional<User> findByEmail(String targetUser);
}
