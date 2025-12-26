package io.github.aplaraujo.tests;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.dto.ProductDTO;
import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.entities.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductFactory {
    public static Product createProduct() {
        Set<Category> categories = new HashSet<>();
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", categories);
        Set<Product> products = new HashSet<>();
        product.getCategories().add(new Category(1L, "Electronics", products));
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = new Product();
        List<CategoryDTO> categories = new ArrayList<>();
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImgUrl(), categories);
    }
}
