package io.github.aplaraujo.mappers;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.dto.ProductDTO;
import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.entities.Product;
import io.github.aplaraujo.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryRepository repository;

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setImgUrl(dto.imgUrl());

        for(CategoryDTO cat: dto.categories()) {
            Category category = repository.findById(cat.id()).orElseThrow(() -> new EntityNotFoundException("Category not found!"));
            product.getCategories().add(category);
        }
        return product;
    }

    public ProductDTO toDTO(Product product) {
        List<CategoryDTO> list = product.getCategories().stream().map(cat -> new CategoryDTO(cat.getId(), cat.getName())).toList();
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImgUrl(), list);
    }
}
