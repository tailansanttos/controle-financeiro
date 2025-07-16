package com.tailan.controle_de_despesas.repositories;

import com.tailan.controle_de_despesas.entities.category.Category;
import com.tailan.controle_de_despesas.entities.category.CategoryType;
import com.tailan.controle_de_despesas.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByIdAndUser(UUID id, User user);
    List<Category> findByUser(User user);
    Optional<Category> findByUserAndNameAndType(User user, String categoryName, CategoryType type);
}
