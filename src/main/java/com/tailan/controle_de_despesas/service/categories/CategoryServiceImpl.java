package com.tailan.controle_de_despesas.service.categories;

import com.tailan.controle_de_despesas.dto.categories.CategoryRequestDto;
import com.tailan.controle_de_despesas.dto.categories.CategoryResponseDto;
import com.tailan.controle_de_despesas.entities.category.Category;
import com.tailan.controle_de_despesas.entities.category.CategoryType;
import com.tailan.controle_de_despesas.entities.user.User;
import com.tailan.controle_de_despesas.mapper.categories.CategoryMapper;
import com.tailan.controle_de_despesas.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        User userLogado = getUserAuthenticate();
        String categoryName = categoryRequestDto.name();
        CategoryType categoryType = categoryRequestDto.type();

        Optional<Category> existingCategory = categoryRepository.findByUserAndNameAndType(userLogado, categoryName, categoryType);
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Categoria já existente");
        }

        Category newCategory = new Category();
        newCategory.setUser(userLogado);
        newCategory.setName(categoryName);
        newCategory.setType(categoryType);
        newCategory.setDateCreation(LocalDateTime.now());

        Category categorySaved = categoryRepository.save(newCategory);
        return categoryMapper.entityToDto(categorySaved);
    }

    @Override
    public CategoryResponseDto getCategory(UUID categoryId) {
        User userLogado = getUserAuthenticate();
        Category exsistingCategory = categoryRepository.findByIdAndUser(categoryId, userLogado).orElseThrow(() -> new UsernameNotFoundException("Categoria não encontrada."));
        return categoryMapper.entityToDto(exsistingCategory);
    }

    @Override
    public List<CategoryResponseDto> getListCategories() {
        User userLogado = getUserAuthenticate();

        List<Category> listCategories = categoryRepository.findByUser(userLogado);
        return listCategories.stream().map(categoryMapper::entityToDto).toList();
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId) {
        User userLogado = getUserAuthenticate();

     Category existingCategory = categoryRepository.findByIdAndUser(categoryId, userLogado).orElseThrow(() -> new UsernameNotFoundException("Categoria nao encontrada."));
     categoryRepository.delete(existingCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto) {
        User userLogado = getUserAuthenticate();

        Category existingCategory = categoryRepository.findByIdAndUser(categoryId,userLogado).orElseThrow(() -> new UsernameNotFoundException("Categoria nao encontrada."));
        if (!existingCategory.getName().equals(categoryRequestDto.name()) || !existingCategory.getType().equals(categoryRequestDto.type())) {
            throw new IllegalArgumentException("Já existe uma categoria com o nome" + existingCategory.getName() + " e o tipo " +  existingCategory.getType() + " para este usuario.");

        }

        existingCategory.setName(categoryRequestDto.name());
        existingCategory.setType(categoryRequestDto.type());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.entityToDto(updatedCategory);
    }

    private User getUserAuthenticate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Verifica se há autenticação
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Acesso negado, usuario não autenticado para criar uma conta.");
        }

        User userLogado = (User) authentication.getPrincipal();
        if (userLogado == null){
            throw new UsernameNotFoundException("Usuario não cadastrado.");
        }
        return userLogado;
    }
}
