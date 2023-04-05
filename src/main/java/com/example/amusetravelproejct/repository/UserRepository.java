package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{



    // 이메일로 유저 찾기
    User findFirstByEmail(String email);

    User findByEmail(String mail);
}
