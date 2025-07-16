package com.tailan.controle_de_despesas.dto.account;

import com.tailan.controle_de_despesas.entities.account.AccountType;
import com.tailan.controle_de_despesas.entities.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponseDto(UUID id,
                                 String name,
                                 BigDecimal initialBalance,
                                 BigDecimal currentBalance,
                                 AccountType type,
                                 LocalDateTime creationDate,
                                 UUID userId) {
}
