package io.github.aplaraujo.repositories;

import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories WHERE p.id = :id")
    Optional<Product> findByIdWithCategories(@PathVariable Long id);

//    Page<Product> findByName(String name, Pageable pageable);
//    Page<Product> findByCategories(Category category, Pageable pageable);
//    Page<Product> findByNameAndCategories(String name, Category category, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.categories WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findByNameWithCategories(@Param("name") String name, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.categories c WHERE :category MEMBER OF p.categories")
    Page<Product> findByCategoriesWithCategories(@Param("category") Category category, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.categories c WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND :category MEMBER OF p.categories")
    Page<Product> findByNameAndCategoriesWithCategories(@Param("name") String name, @Param("category") Category category, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.categories")
    Page<Product> findAllWithCategories(Pageable pageable);
}
