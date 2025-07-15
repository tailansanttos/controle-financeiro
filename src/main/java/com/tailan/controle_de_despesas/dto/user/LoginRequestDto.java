package com.tailan.controle_de_despesas.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Email não pode está em branco.")
        @Email
        String email,
        @NotBlank(message = "Senha não pode está em branco.")
        String password) {
}
