package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisplayCategoryRepository extends JpaRepository<Category, Long> {

//    Optional<Category> findByHashTag(HashTag hashTag);
}
