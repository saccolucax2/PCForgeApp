package it.unisannio.pcforgeapp.repository;

import it.unisannio.pcforgeapp.model.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildRepository extends JpaRepository<Build, Long> {
    // Puoi aggiungere query specifiche se necessario (per ora teniamo la base)
}