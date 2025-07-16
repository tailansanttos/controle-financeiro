package com.tailan.controle_de_despesas.controllers;

import com.tailan.controle_de_despesas.dto.account.AccountRequestDto;
import com.tailan.controle_de_despesas.dto.account.AccountResponseDto;
import com.tailan.controle_de_despesas.dto.api.ApiResponseDto;
import com.tailan.controle_de_despesas.service.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/create")
    public ResponseEntity<ApiResponseDto> createAccount(@RequestBody AccountRequestDto accountRequest) {
        AccountResponseDto accountResponse = accountService.createAccountUser(accountRequest);
        return ResponseEntity.ok(new ApiResponseDto("Conta cadastrada com sucesso", accountResponse));
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponseDto> getListAccounts() {
        List<AccountResponseDto> listAccounts = accountService.getListAccountUsers();
        return ResponseEntity.ok(new ApiResponseDto("Suas contas cadastradas. ",  listAccounts));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponseDto> getAccountById(@PathVariable UUID accountId) {
        AccountResponseDto accountResponse = accountService.getAccountById(accountId);
        return ResponseEntity.ok(new ApiResponseDto("Conta encontrada com sucesso. ",  accountResponse));
    }

    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<ApiResponseDto> deleteAccountById(@PathVariable UUID accountId) {
        this.accountService.deleteAccountUser(accountId);
        return ResponseEntity.noContent().build();
    }
}
