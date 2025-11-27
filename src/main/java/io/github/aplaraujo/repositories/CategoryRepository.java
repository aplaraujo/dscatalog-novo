package io.github.aplaraujo.repositories;

import io.github.aplaraujo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
