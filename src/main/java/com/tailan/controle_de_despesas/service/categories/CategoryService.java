package com.tailan.controle_de_despesas.service.categories;

import com.tailan.controle_de_despesas.dto.categories.CategoryRequestDto;
import com.tailan.controle_de_despesas.dto.categories.CategoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto getCategory(UUID categoryId);
    List<CategoryResponseDto> getListCategories();
    void deleteCategory(UUID categoryId);

    CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto);
}
