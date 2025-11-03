package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;



     //Test 1: Buscar visita por ID

    @Test
    public void testFindVisitById() {
        String EXPECTED_DESCRIPTION = "rabies shot";
        Integer ID = 1;

        VisitDTO visit = null;
        try {
            visit = visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("Visit found: " + visit.toString());

        assertEquals(EXPECTED_DESCRIPTION, visit.getDescription());
        assertNotNull(visit.getPetId());
    }


    //Test 2: Buscar visitas por Pet ID

    @Test
    public void testFindVisitsByPetId() {
        Integer PET_ID = 8;
        int SIZE_EXPECTED = 2; // Según data.sql, pet 8 tiene 2 visitas

        List<VisitDTO> visits = visitService.findByPetId(PET_ID);

        log.info("Visits found for pet " + PET_ID + ": " + visits.size());

        assertEquals(SIZE_EXPECTED, visits.size());
    }


     //Test 3: Buscar visitas por Vet ID

    @Test
    public void testFindVisitsByVetId() {
        Integer VET_ID = 2;
        int MIN_EXPECTED = 2; // Según data.sql

        List<VisitDTO> visits = visitService.findByVetId(VET_ID);

        log.info("Visits found for vet " + VET_ID + ": " + visits.size());

        assertTrue(visits.size() >= MIN_EXPECTED);
    }


     //Test 4: Buscar todas las visitas

    @Test
    public void testFindAllVisits() {
        int MIN_EXPECTED = 6; // Según data.sql hay 6 visitas

        List<VisitDTO> visits = visitService.findAll();

        log.info("Total visits found: " + visits.size());

        assertTrue(visits.size() >= MIN_EXPECTED);
    }


     // Test 5: Crear una nueva visita

    @Test
    public void testCreateVisit() {
        Integer PET_ID = 1;
        Integer VET_ID = 1;
        LocalDate VISIT_DATE = LocalDate.of(2025, 10, 28);
        String DESCRIPTION = "Control mensual";
        BigDecimal COST = new BigDecimal("80.00");

        VisitDTO visitDTO = VisitDTO.builder()
                .petId(PET_ID)
                .vetId(VET_ID)
                .visitDate(VISIT_DATE)
                .description(DESCRIPTION)
                .cost(COST)
                .build();

        VisitDTO newVisitDTO = visitService.create(visitDTO);

        log.info("VISIT CREATED: " + newVisitDTO.toString());

        assertNotNull(newVisitDTO.getId());
        assertEquals(PET_ID, newVisitDTO.getPetId());
        assertEquals(VET_ID, newVisitDTO.getVetId());
        assertEquals(DESCRIPTION, newVisitDTO.getDescription());
        assertEquals(COST, newVisitDTO.getCost());
    }



    //Test 6: Actualizar una visita

    @Test
    public void testUpdateVisit() {
        Integer PET_ID = 2;
        Integer VET_ID = 2;
        String ORIGINAL_DESCRIPTION = "Chequeo inicial";
        String UPDATED_DESCRIPTION = "Chequeo inicial - Vacunación completa";
        BigDecimal ORIGINAL_COST = new BigDecimal("75.00");
        BigDecimal UPDATED_COST = new BigDecimal("95.00");

        // Crear visita
        VisitDTO visitDTO = VisitDTO.builder()
                .petId(PET_ID)
                .vetId(VET_ID)
                .visitDate(LocalDate.now())
                .description(ORIGINAL_DESCRIPTION)
                .cost(ORIGINAL_COST)
                .build();

        VisitDTO visitDTOCreated = visitService.create(visitDTO);
        log.info("Visit created: " + visitDTOCreated.toString());

        // Actualizar descripción y costo
        visitDTOCreated.setDescription(UPDATED_DESCRIPTION);
        visitDTOCreated.setCost(UPDATED_COST);

        VisitDTO visitDTOUpdated = visitService.update(visitDTOCreated);
        log.info("Visit updated: " + visitDTOUpdated.toString());

        assertEquals(UPDATED_DESCRIPTION, visitDTOUpdated.getDescription());
        assertEquals(UPDATED_COST, visitDTOUpdated.getCost());
    }



     //Test 7: Eliminar una visita

    @Test
    public void testDeleteVisit() {
        Integer PET_ID = 3;
        Integer VET_ID = 3;
        String DESCRIPTION = "Visita temporal para test";

        // Crear visita temporal
        VisitDTO visitDTO = VisitDTO.builder()
                .petId(PET_ID)
                .vetId(VET_ID)
                .visitDate(LocalDate.now())
                .description(DESCRIPTION)
                .cost(new BigDecimal("50.00"))
                .build();

        VisitDTO newVisitDTO = visitService.create(visitDTO);
        log.info("Visit created for deletion: " + newVisitDTO.toString());

        // Eliminar
        try {
            visitService.delete(newVisitDTO.getId());
            log.info("Visit deleted with ID: " + newVisitDTO.getId());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        // Verificar que no existe
        try {
            visitService.findById(newVisitDTO.getId());
            fail("Visit should not exist after deletion");
        } catch (VisitNotFoundException e) {
            assertTrue(true);
            log.info("Visit successfully deleted");
        }
    }
}