package com.tailan.controle_de_despesas.repositories;

import com.tailan.controle_de_despesas.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
