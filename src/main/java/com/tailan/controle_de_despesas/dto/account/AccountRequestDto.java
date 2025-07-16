package com.tailan.controle_de_despesas.dto.account;

import com.tailan.controle_de_despesas.entities.account.AccountType;

import java.math.BigDecimal;

public record AccountRequestDto(String name, BigDecimal initialBalance, AccountType type) {
}
