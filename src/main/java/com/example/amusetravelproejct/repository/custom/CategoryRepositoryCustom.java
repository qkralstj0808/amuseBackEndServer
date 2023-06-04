package com.example.amusetravelproejct.repository.custom;


import com.example.amusetravelproejct.domain.Category;

public interface CategoryRepositoryCustom {

    Category findByCategoryName(String category_name);
}
