package com.example.amusetravelproejct.repository.custom;


import com.example.amusetravelproejct.domain.Category;

import java.util.List;

public interface CategoryRepositoryCustom {

    Category findByCategoryName(String category_name);
    List<Category> findgreaterSequence(Long sequence);

    List<Category> findAllByDisable(Boolean disable);
    List<Category> findAllByDisableSortBySequence(Boolean disable);
}
