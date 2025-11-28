package io.github.aplaraujo.mapper;

import io.github.aplaraujo.dto.CategoryDTO;
import io.github.aplaraujo.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "name", target = "name")
    Category toEntity(CategoryDTO dto);
    CategoryDTO toDTO(Category category);
}
