package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.TempHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempHashTagRepository extends JpaRepository<TempHashTag, Long> {
    Optional<TempHashTag> findByHashTag(String data);
}
