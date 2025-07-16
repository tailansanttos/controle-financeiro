package com.tailan.controle_de_despesas.repositories;

import com.tailan.controle_de_despesas.entities.account.Account;
import com.tailan.controle_de_despesas.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByUser(User user);

    Optional<Account> findByIdAndUser(UUID id, User user);
}
