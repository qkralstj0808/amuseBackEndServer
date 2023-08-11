package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.CategoryPageComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryPageComponentRepository extends JpaRepository<CategoryPageComponent,Long> {

    List<CategoryPageComponent> findByCategoryIdOrderBySequence(Long category_id);
}
