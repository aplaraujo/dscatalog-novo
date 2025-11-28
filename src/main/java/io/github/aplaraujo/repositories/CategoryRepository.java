package io.github.aplaraujo.repositories;

import io.github.aplaraujo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
    Optional<Category> searchByName(String name);

}
