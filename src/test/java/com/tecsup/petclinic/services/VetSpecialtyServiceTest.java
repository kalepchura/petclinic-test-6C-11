package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    void shouldFindAllVetSpecialties() {
        int MIN_EXPECTED = 5; // Según data.sql hay 5 relaciones

        List<VetSpecialtyDTO> allVetSpecialties = vetSpecialtyService.findAll();

        log.info("Total VetSpecialties found: {}", allVetSpecialties.size());
        assertTrue(allVetSpecialties.size() >= MIN_EXPECTED);
    }

    @Test
    void shouldFindVetSpecialtiesByVet() {
        Integer VET_ID = 2; // Helen Leary tiene especialidades
        int MIN_EXPECTED = 1;

        List<VetSpecialtyDTO> vetSpecialties = vetSpecialtyService.findByVetId(VET_ID);

        log.info("VetSpecialties found for vet {}: {}", VET_ID, vetSpecialties.size());
        assertTrue(vetSpecialties.size() >= MIN_EXPECTED);
    }

    @Test
    void shouldFindVetSpecialtiesBySpecialty() {
        Integer SPECIALTY_ID = 1; // Radiología
        int MIN_EXPECTED = 2; // Helen Leary y Henry Stevens

        List<VetSpecialtyDTO> vetSpecialties = vetSpecialtyService.findBySpecialtyId(SPECIALTY_ID);

        log.info("VetSpecialties found for specialty {}: {}", SPECIALTY_ID, vetSpecialties.size());
        assertTrue(vetSpecialties.size() >= MIN_EXPECTED);
    }

    @Test
    void shouldFindVetSpecialtiesByVetAndSpecialty() {
        Integer VET_ID = 3;
        Integer SPECIALTY_ID = 2;

        List<VetSpecialtyDTO> vetSpecialties = vetSpecialtyService.findByVetIdAndSpecialtyId(VET_ID, SPECIALTY_ID);

        log.info("VetSpecialties found for vet {} and specialty {}: {}", VET_ID, SPECIALTY_ID, vetSpecialties.size());
        assertFalse(vetSpecialties.isEmpty());
    }

    @Test
    void shouldFindPrimaryVetSpecialties() {
        List<VetSpecialtyDTO> primarySpecialties = vetSpecialtyService.findByIsPrimary(true);

        log.info("Primary VetSpecialties found: {}", primarySpecialties.size());
        assertFalse(primarySpecialties.isEmpty());
    }

    @Test
    void shouldCreateVetSpecialty() {
        // Usar una combinación que NO exista en data.sql para evitar duplicados
        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(1) // James Carter - NO tiene especialidades en data.sql
                .specialtyId(3) // Dentistry
                .certificationDate(LocalDate.of(2023, 1, 15))
                .yearsExperience(3)
                .isPrimary(false)
                .notes("Additional certification")
                .build();

        VetSpecialtyDTO createdVetSpecialty = vetSpecialtyService.create(vetSpecialtyDTO);

        log.info("VetSpecialty created: {}", createdVetSpecialty);
        assertNotNull(createdVetSpecialty);
        assertEquals(1, createdVetSpecialty.getVetId());
        assertEquals(3, createdVetSpecialty.getSpecialtyId());
        assertEquals(3, createdVetSpecialty.getYearsExperience());
    }

    @Test
    void shouldUpdateVetSpecialty() {
        // Primero crear una relación que podamos actualizar
        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(6) // Sharon Jenkins - NO tiene especialidades en data.sql
                .specialtyId(2) // Surgery
                .certificationDate(LocalDate.now())
                .yearsExperience(2)
                .isPrimary(false)
                .notes("Initial note")
                .build();

        // Crear la relación
        VetSpecialtyDTO createdVetSpecialty = vetSpecialtyService.create(vetSpecialtyDTO);

        // Actualizar
        createdVetSpecialty.setYearsExperience(5);
        createdVetSpecialty.setNotes("Updated note");
        createdVetSpecialty.setIsPrimary(true);

        VetSpecialtyDTO updatedVetSpecialty = null;
        try {
            updatedVetSpecialty = vetSpecialtyService.update(createdVetSpecialty);
        } catch (VetSpecialtyNotFoundException e) {
            fail("Should not throw exception: " + e.getMessage());
        }

        log.info("VetSpecialty updated: {}", updatedVetSpecialty);
        assertEquals(5, updatedVetSpecialty.getYearsExperience());
        assertEquals("Updated note", updatedVetSpecialty.getNotes());
        assertTrue(updatedVetSpecialty.getIsPrimary());
    }

    @Test
    void shouldDeleteVetSpecialty() {
        // Crear una relación temporal para eliminar
        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(5) // Henry Stevens - ya tiene una especialidad, pero creamos otra
                .specialtyId(3) // Dentistry (diferente a la que ya tiene)
                .certificationDate(LocalDate.now())
                .yearsExperience(1)
                .isPrimary(false)
                .notes("Temporary for deletion")
                .build();

        VetSpecialtyDTO createdVetSpecialty = vetSpecialtyService.create(vetSpecialtyDTO);

        // Eliminar usando el método que recibe DTO
        try {
            vetSpecialtyService.delete(createdVetSpecialty);
        } catch (VetSpecialtyNotFoundException e) {
            fail("Should not throw exception: " + e.getMessage());
        }

        // Verificar que fue eliminado - ya no debe existir esta combinación
        List<VetSpecialtyDTO> remaining = vetSpecialtyService.findByVetIdAndSpecialtyId(5, 3);
        boolean found = remaining.stream()
                .anyMatch(vs -> vs.getVetId().equals(5) && vs.getSpecialtyId().equals(3));
        assertFalse(found, "The VetSpecialty should have been deleted");
    }

    @Test
    void shouldFindSpecificVetSpecialtyByCompositeId() {
        // Usar una relación que SÍ existe en data.sql
        Integer VET_ID = 2; // Helen Leary
        Integer SPECIALTY_ID = 1; // Radiology

        VetSpecialtyDTO foundVetSpecialty = null;
        try {
            foundVetSpecialty = vetSpecialtyService.findVetSpecialtyByCompositeId(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail("Should not throw exception: " + e.getMessage());
        }

        log.info("Specific VetSpecialty found: {}", foundVetSpecialty);
        assertNotNull(foundVetSpecialty);
        assertEquals(VET_ID, foundVetSpecialty.getVetId());
        assertEquals(SPECIALTY_ID, foundVetSpecialty.getSpecialtyId());
        assertNotNull(foundVetSpecialty.getCertificationDate());
    }

    @Test
    void shouldThrowExceptionWhenSpecificVetSpecialtyNotFound() {
        // Usar una combinación que NO existe
        Integer VET_ID = 999;
        Integer SPECIALTY_ID = 999;

        assertThrows(VetSpecialtyNotFoundException.class, () -> {
            vetSpecialtyService.findVetSpecialtyByCompositeId(VET_ID, SPECIALTY_ID);
        });
    }
}