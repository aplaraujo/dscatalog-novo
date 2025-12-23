package io.github.aplaraujo.services;

import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.entities.Product;
import io.github.aplaraujo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return repository.findByIdWithCategories(id);
    }

    public Page<Product> search(String name, Category category, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        if (name != null && category != null) {
            return repository.findByNameAndCategoriesWithCategories(name, category, pageable);
        }

        if (name != null) {
            return repository.findByNameWithCategories(name, pageable);
        }

        if (category != null) {
            return repository.findByCategoriesWithCategories(category, pageable);
        }

        return repository.findAllWithCategories(pageable);
    }

    public void update(Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product not found!");
        }
        repository.save(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }
}
