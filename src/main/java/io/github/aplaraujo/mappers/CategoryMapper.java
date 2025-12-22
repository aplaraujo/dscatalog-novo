package io.github.aplaraujo.mappers;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.id());
        category.setName(dto.name());
        return category;
    }

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
