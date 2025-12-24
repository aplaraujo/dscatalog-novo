package io.github.aplaraujo.controllers;

import io.github.aplaraujo.dto.ProductDTO;
import io.github.aplaraujo.entities.Category;
import io.github.aplaraujo.entities.Product;
import io.github.aplaraujo.mappers.ProductMapper;
import io.github.aplaraujo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements GenericController {
    private final ProductService service;
    private final ProductMapper mapper;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        service.save(product);
        var url = generateHeaderLocation(product.getId());
        return ResponseEntity.created(url).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") String id) {
        var productId = Long.parseLong(id);
        return service.findById(productId).map(prod -> {
            var dto = mapper.toDTO(prod);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false)Category category,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
            ) {
        Page<Product> result = service.search(name, category, page, pageSize);
        Page<ProductDTO> page1 = result.map(mapper::toDTO);
        return ResponseEntity.ok(page1);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody ProductDTO dto) {
        var productId = Long.parseLong(id);
        return service.findById(productId).map(prod -> {
            Product entity = mapper.toEntity(dto);
            prod.setName(entity.getName());
            prod.setDescription(entity.getDescription());
            prod.setPrice(entity.getPrice());
            prod.setImgUrl(entity.getImgUrl());
            service.update(prod);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        var productId = Long.parseLong(id);
        return service.findById(productId).map(prod -> {
            service.delete(prod);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
