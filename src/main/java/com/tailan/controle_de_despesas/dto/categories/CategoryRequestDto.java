package com.tailan.controle_de_despesas.dto.categories;

import com.tailan.controle_de_despesas.entities.category.CategoryType;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDto(
        @NotNull(message = "Campo não pode ser nulo.")
        String name,
        @NotNull(message = "Campo não pode ser nulo.")
        CategoryType type) {
}
