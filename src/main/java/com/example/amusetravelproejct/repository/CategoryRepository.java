package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.repository.custom.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {


}
