package io.github.aplaraujo.tests;

import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.entities.Product;

import java.util.HashSet;
import java.util.Set;

public class ProductFactory {
    public static Product createProduct() {
        Set<Category> categories = new HashSet<>();
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", categories);
        Set<Product> products = new HashSet<>();
        product.getCategories().add(new Category(1L, "Electronics", products));
        return product;
    }

}
