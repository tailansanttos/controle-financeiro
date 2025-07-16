package com.tailan.controle_de_despesas.controllers;

import com.tailan.controle_de_despesas.dto.api.ApiResponseDto;
import com.tailan.controle_de_despesas.dto.categories.CategoryRequestDto;
import com.tailan.controle_de_despesas.dto.categories.CategoryResponseDto;
import com.tailan.controle_de_despesas.service.categories.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category/create")
    public ResponseEntity<ApiResponseDto> createCategory(@RequestBody @Valid  CategoryRequestDto categoryRequestDto){
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok(new ApiResponseDto("Category created", categoryResponseDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseDto> getAllCategories(){
        List<CategoryResponseDto> listCategories = categoryService.getListCategories();
        return ResponseEntity.ok(new ApiResponseDto("Category list", listCategories));
    }

    @PutMapping("/category/{categoryId}/update")
    public ResponseEntity<ApiResponseDto>  updateCategory(@PathVariable UUID categoryId, @RequestBody @Valid CategoryRequestDto categoryRequestDto){
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId, categoryRequestDto);
        return ResponseEntity.ok(new ApiResponseDto("Category updated", categoryResponseDto));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponseDto> getCategoryById(@PathVariable UUID categoryId){
        CategoryResponseDto categoryResponse = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(new ApiResponseDto("Category", categoryResponse));
    }
}
