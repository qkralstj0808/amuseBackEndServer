package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ItemHashTagRepository extends JpaRepository<ItemHashTag, Long> , QuerydslPredicateExecutor<ItemHashTag> {
    List<ItemHashTag> findByHashTag(String hash_tag);




}
