package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemHashTag;
import com.example.amusetravelproejct.domain.TempHashTag;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Optional;

public interface ItemHashTagRepository extends JpaRepository<ItemHashTag, Long> , QuerydslPredicateExecutor<ItemHashTag> {
    List<ItemHashTag> findByHashTag(String hash_tag);




}
