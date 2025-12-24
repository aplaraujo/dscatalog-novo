package io.github.aplaraujo.repositories;

import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.tests.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class CategoryRepositoryTests extends BaseRepositoryTest{

    @Autowired
    private CategoryRepository repository;

    private long existingId;
    private long countTotalCategories;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        countTotalCategories = 3L;
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Category> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Category category = CategoryFactory.createCategory();
        category.setId(null);
        category = repository.save(category);
        Optional<Category> result = repository.findById(category.getId());

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(countTotalCategories + 1L, category.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(result.get(), category);
    }
}
