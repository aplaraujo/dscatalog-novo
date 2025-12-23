package io.github.aplaraujo.dto;

import java.util.List;

public record ProductDTO(Long id, String name, String description, Double price, String imgUrl, List<CategoryDTO> categories) {
}
