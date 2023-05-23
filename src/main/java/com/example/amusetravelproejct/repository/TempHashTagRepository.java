package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.TempHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface TempHashTagRepository extends JpaRepository<TempHashTag, Long> {
    Optional<TempHashTag> findByHashTag(String data);
}
