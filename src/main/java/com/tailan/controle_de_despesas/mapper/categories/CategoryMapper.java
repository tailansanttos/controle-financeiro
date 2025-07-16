package com.tailan.controle_de_despesas.mapper.categories;

import com.tailan.controle_de_despesas.dto.categories.CategoryResponseDto;
import com.tailan.controle_de_despesas.entities.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponseDto entityToDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getUser().getName(),
                category.getName(),
                category.getType(),
                category.getDateCreation()
        );
    }
}
