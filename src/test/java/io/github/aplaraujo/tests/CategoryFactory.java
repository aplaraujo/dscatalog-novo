package io.github.aplaraujo.tests;

import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.entities.Product;

import java.util.HashSet;
import java.util.Set;

public class CategoryFactory {
    public static Category createCategory() {
        Set<Product> products = new HashSet<>();
        Category category = new Category(1L, "Roupas e Acessórios", products);
        return category;
    }
}
