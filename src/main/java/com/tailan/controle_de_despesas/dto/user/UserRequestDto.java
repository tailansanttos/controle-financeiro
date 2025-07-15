package com.tailan.controle_de_despesas.dto.user;

import com.tailan.controle_de_despesas.entities.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRequestDto(
        @NotNull(message = "Campo não pode ser nulo.")
        String name,
        @NotNull(message = "Campo não pode ser nulo.")
        @CPF(message = "Campo cpf precisa ser válid.")
        String cpf,
        @NotNull(message = "Campo não pode ser nulo.")
        @Email(message = "Email deve ser válido")
        String email,
        @NotNull(message = "Campo não pode ser nulo.")
        String password) {

}
