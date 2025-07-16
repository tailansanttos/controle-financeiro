package com.tailan.controle_de_despesas.service.account;

import com.tailan.controle_de_despesas.dto.account.AccountRequestDto;
import com.tailan.controle_de_despesas.dto.account.AccountResponseDto;
import com.tailan.controle_de_despesas.entities.account.Account;
import com.tailan.controle_de_despesas.entities.user.User;
import com.tailan.controle_de_despesas.mapper.account.AccountMapper;
import com.tailan.controle_de_despesas.repositories.AccountRepository;
import com.tailan.controle_de_despesas.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountMapper  accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, UserService userService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.accountMapper = accountMapper;
    }


    //OBTEM O USUARIO AUTENTICADO DO SECURITYCONTEXTHOLDER
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Acesso negado, usuario não autenticado para criar uma conta.");
        }
        User userLogado = (User) authentication.getPrincipal();
        if (userLogado == null) {
            throw new IllegalArgumentException("Usuario Não cadastrado.");
        }

        return userLogado;
    }


    @Override
    @Transactional
    public AccountResponseDto createAccountUser(AccountRequestDto accountRequestDto) {
        User userLogado = getAuthenticatedUser();

        BigDecimal balanceInitial = accountRequestDto.initialBalance();

        if (balanceInitial == null || balanceInitial.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor a ser adicionado deve ser positivo.");
        }

        Account account = new Account();
        account.setUser(userLogado);
        account.setCreationDate(LocalDateTime.now());
        account.setInitialBalance(balanceInitial);
        account.setCurrentBalance(balanceInitial);
        account.setType(accountRequestDto.type());
        account.setName(accountRequestDto.name());
        Account savedAccount =  accountRepository.save(account);

        return accountMapper.entityToDto(savedAccount);
    }

    @Override
    public List<AccountResponseDto> getListAccountUsers() {
        //PEGA AS CONTAS DO USUARIO LOGADO
        User userLogado = getAuthenticatedUser();
        List<Account> listAccounts = accountRepository.findByUser(userLogado);
        return listAccounts.stream().map(accountMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public AccountResponseDto getAccountById(UUID accountId) {
        User userLogado = getAuthenticatedUser();
        Account account = accountRepository.findByIdAndUser(accountId, userLogado).orElseThrow(() -> new UsernameNotFoundException("Conta nao encontrada."));
        return accountMapper.entityToDto(account);
    }

    @Override
    @Transactional
    public void deleteAccountUser(UUID accountId) {
        User userLogado = getAuthenticatedUser();

        Account account = accountRepository.findByIdAndUser(accountId, userLogado).orElseThrow(() -> new UsernameNotFoundException("Conta nao encontrada."));
        accountRepository.delete(account);
    }

    @Override
    @Transactional
    public void adicionarSaldoAccount(Account account, BigDecimal saldo) {
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor a ser adicionado deve ser positivo.");
        }

        account.setCurrentBalance(account.getCurrentBalance().add(saldo));
        accountRepository.save(account);

    }

    @Override
    @Transactional
    public void removerSaldoAccount(Account account, BigDecimal saldo) {
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor a ser adicionado deve ser positivo.");
        }

        account.setCurrentBalance(account.getCurrentBalance().subtract(saldo));
        accountRepository.save(account);
    }
}
