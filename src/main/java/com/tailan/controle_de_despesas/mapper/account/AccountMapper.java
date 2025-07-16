package com.tailan.controle_de_despesas.mapper.account;

import com.tailan.controle_de_despesas.dto.account.AccountResponseDto;
import com.tailan.controle_de_despesas.dto.user.UserResponseDto;
import com.tailan.controle_de_despesas.entities.account.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountResponseDto entityToDto(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getName(),
                account.getInitialBalance(),
                account.getCurrentBalance(),
                account.getType(),
                account.getCreationDate(),
                account.getUser().getId());
    }
}
