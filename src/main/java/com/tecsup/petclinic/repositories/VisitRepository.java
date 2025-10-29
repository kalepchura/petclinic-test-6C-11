package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {

    // Buscar visitas por mascota
    List<Visit> findByPetId(Integer petId);

    // Buscar visitas por veterinario
    List<Visit> findByVetId(Integer vetId);

    // Buscar visitas por descripción
    List<Visit> findByDescriptionContaining(String description);
}