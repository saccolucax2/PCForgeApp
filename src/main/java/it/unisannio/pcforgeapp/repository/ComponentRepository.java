package it.unisannio.pcforgeapp.repository;

import it.unisannio.pcforgeapp.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    // Cercare tutti i componenti di una certa categoria, ordinati per prezzo ascendente
    List<Component> findByCategoryOrderByPriceAsc(String category);
}