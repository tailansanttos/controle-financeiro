package com.tailan.controle_de_despesas.dto.user;

import com.tailan.controle_de_despesas.entities.user.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        LocalDateTime creationDate,
        UserRole role) {
}
