package io.github.aplaraujo.services;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.repositories.CategoryRepository;
import io.github.aplaraujo.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(cat -> new CategoryDTO(cat.getId(), cat.getName())).toList();
    }

    public CategoryDTO findById(Long id) {
        Optional<Category> result = categoryRepository.findById(id);
        Category category = result.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new CategoryDTO(category.getId(), category.getName());
    }

    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        copyDtoToEntity(dto, entity);
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity.getId(), entity.getName());
    }

    private void copyDtoToEntity(CategoryDTO dto, Category entity) {
        entity.setName(dto.name());
    }
}
