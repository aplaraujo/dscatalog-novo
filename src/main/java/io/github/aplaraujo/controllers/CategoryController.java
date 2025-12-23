package io.github.aplaraujo.controllers;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.mappers.CategoryMapper;
import io.github.aplaraujo.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController implements GenericController{
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CategoryDTO dto) {
        Category cat = categoryMapper.toEntity(dto);
        categoryService.save(cat);
        var url = generateHeaderLocation(cat.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") String id) {
        var categoryId = Long.parseLong(id);
        return categoryService.findById(categoryId).map(cat -> {
            CategoryDTO dto = categoryMapper.toDTO(cat);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CategoryDTO> categories() {
        return categoryService.categories();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody CategoryDTO dto) {
        var categoryId = Long.parseLong(id);
        return categoryService.findById(categoryId).map(cat -> {
            Category entity = categoryMapper.toEntity(dto);
            cat.setName(entity.getName());
            categoryService.update(cat);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        var categoryId = Long.parseLong(id);
        return categoryService.findById(categoryId).map(cat -> {
            categoryService.delete(cat);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
