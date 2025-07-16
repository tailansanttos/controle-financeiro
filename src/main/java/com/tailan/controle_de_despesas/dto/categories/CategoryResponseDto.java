package com.tailan.controle_de_despesas.dto.categories;

import com.tailan.controle_de_despesas.entities.category.CategoryType;
import com.tailan.controle_de_despesas.entities.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponseDto(UUID categoryId,
                                  String userName,
                                  String categoryName,
                                  CategoryType type,
                                  LocalDateTime dateCreation) {
}
