package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CategoryRepositoryTest {


    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {
        // Create a new Category entity
        Category category = new Category();
        category.setCategoryName("Test Category");

        // Save the Category entity to the database
        categoryRepository.save(category);

        // Verify that the Category entity was saved successfully
        assertNotNull(category.getId());
        assertEquals("Test Category", category.getCategoryName());
    }

    @Test
    public void read() {
        // Create a new Category entity
        Category category = new Category();
        category.setCategoryName("Test Category");

        // Save the Category entity to the database
        categoryRepository.save(category);

        // Retrieve the Category entity from the database
        Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);

        // Verify that the Category entity was retrieved successfully
        assertNotNull(foundCategory);
        assertEquals(category.getId(), foundCategory.getId());
        assertEquals(category.getCategoryName(), foundCategory.getCategoryName());
    }

    @Test
    public void update() {
        // Create a new Category entity
        Category category = new Category();
        category.setCategoryName("Test Category");

        // Save the Category entity to the database
        categoryRepository.save(category);

        // Update the Category entity's name
        category.setCategoryName("Updated Test Category");

        // Save the updated Category entity to the database
        categoryRepository.save(category);

        // Retrieve the Category entity from the database
        Category updatedCategory = categoryRepository.findById(category.getId()).orElse(null);

        // Verify that the Category entity was updated successfully
        assertNotNull(updatedCategory);
        assertEquals("Updated Test Category", updatedCategory.getCategoryName());
    }

    @Test
    public void delete() {
        // Create a new Category entity
        Category category = new Category();
        category.setCategoryName("Test Category");

        // Save the Category entity to the database
        categoryRepository.save(category);

        // Delete the Category entity from the database
        categoryRepository.delete(category);

        // Verify that the Category entity was deleted successfully
        assertFalse(categoryRepository.existsById(category.getId()));
    }


}