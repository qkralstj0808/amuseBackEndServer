package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.AdminAdvertisement;
import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getId());
        if (existingCategory.isPresent()) {
            return categoryRepository.save(category);
        } else {
            throw new ResourceNotFoundException("Category not found with id " + category.getId());
        }
    }

    public void deleteCategory(Long id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
    }

}
