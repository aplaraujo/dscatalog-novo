package io.github.aplaraujo.services;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.repositories.CategoryRepository;
import io.github.aplaraujo.services.exceptions.ResourceNotFoundException;
import io.github.aplaraujo.validators.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.github.aplaraujo.repositories.specs.CategorySpecs.nameLike;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    public List<Category> search(String name) {
        if (name != null) {
            return categoryRepository.findByName(name);
        }
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category insert(Category category) {
        categoryValidator.validate(category);
        return categoryRepository.save(category);
    }

    public void update(Category category) {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Category not found!");
        }

        categoryValidator.validate(category);
        categoryRepository.save(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public Page<Category> findAllByPage(String name, Integer pagina, Integer tamanhoPagina) {
        Specification<Category> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (name != null) {
            specification = specification.and(nameLike(name));
        }

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        return categoryRepository.findAll(specification, pageable);
    }

}
