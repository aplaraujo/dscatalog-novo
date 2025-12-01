package io.github.aplaraujo.repositories;

import io.github.aplaraujo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
