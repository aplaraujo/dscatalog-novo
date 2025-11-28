package io.github.aplaraujo.validators;

import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.repositories.CategoryRepository;
import io.github.aplaraujo.services.exceptions.DuplicateRecordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public void validate(Category category) {
        if (doesCategoryExist(category)) {
            throw new DuplicateRecordException("This category is already registered!");
        }
    }

    private boolean doesCategoryExist(Category category){
        Optional<Category> categoryFound = categoryRepository.searchByName(category.getName());

        if (category.getId() == null) {
            return categoryFound.isPresent();
        }

        return categoryFound.isPresent() && !category.getId().equals(categoryFound.get().getId());
    }
}
