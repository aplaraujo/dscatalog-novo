package io.github.aplaraujo.controllers;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.mapper.CategoryMapper;
import io.github.aplaraujo.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
@RequiredArgsConstructor
public class CategoryController implements GenericController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") String id) {
        var categoryId = Long.parseLong(id);
        return categoryService.findById(categoryId).map(cat -> {
            CategoryDTO dto = categoryMapper.toDTO(cat);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> search(@RequestParam(value="name", required=false) String name) {
        List<Category> list = categoryService.search(name);
        List<CategoryDTO> dto = list.stream().map(categoryMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody CategoryDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        categoryService.insert(category);
        URI location = generateHeaderLocation(category.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody CategoryDTO dto) {
        var categoryId = Long.parseLong(id);
        Optional<Category> category = categoryService.findById(categoryId);

        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var result = category.get();
        result.setName(dto.name());
        categoryService.update(result);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        var categoryId = Long.parseLong(id);
        Optional<Category> category = categoryService.findById(categoryId);

        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoryService.delete(category.get());
        return ResponseEntity.noContent().build();
    }
}
