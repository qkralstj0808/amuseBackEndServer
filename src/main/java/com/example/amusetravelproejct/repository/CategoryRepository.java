package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
