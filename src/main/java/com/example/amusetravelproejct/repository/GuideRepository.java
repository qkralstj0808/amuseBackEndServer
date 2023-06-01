package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    Optional<Guide> findByCode(String code);
}
