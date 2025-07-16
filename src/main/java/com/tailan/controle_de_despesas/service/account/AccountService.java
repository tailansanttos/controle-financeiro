package com.tailan.controle_de_despesas.service.account;

import com.tailan.controle_de_despesas.dto.account.AccountRequestDto;
import com.tailan.controle_de_despesas.dto.account.AccountResponseDto;
import com.tailan.controle_de_despesas.entities.account.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    public AccountResponseDto createAccountUser(AccountRequestDto accountRequestDto);
    public List<AccountResponseDto> getListAccountUsers();
    public AccountResponseDto getAccountById(UUID accountId);
    public void deleteAccountUser(UUID accountId);

    public void adicionarSaldoAccount(Account account, BigDecimal saldo);
    public void removerSaldoAccount(Account account, BigDecimal saldo);
}
