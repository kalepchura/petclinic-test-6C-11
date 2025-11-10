package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    /**
     * CASO 6 - Prueba para crear relación vet-specialty
     */
    @Test
    public void testCreateVetSpecialty() {

        Integer VET_ID = 1;
        Integer SPECIALTY_ID = 1;
        LocalDate CERTIFICATION_DATE = LocalDate.now();
        int YEARS_EXPERIENCE = 5;
        boolean IS_PRIMARY = true;

        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .certificationDate(CERTIFICATION_DATE)
                .yearsExperience(YEARS_EXPERIENCE)
                .isPrimary(IS_PRIMARY)
                .build();

        VetSpecialtyDTO newVetSpecialtyDTO = this.vetSpecialtyService.create(vetSpecialtyDTO);

        log.info("VetSpecialty CREATED :" + newVetSpecialtyDTO.toString());

        assertNotNull(newVetSpecialtyDTO);
        assertEquals(VET_ID, newVetSpecialtyDTO.getVetId());
        assertEquals(SPECIALTY_ID, newVetSpecialtyDTO.getSpecialtyId());
        assertEquals(CERTIFICATION_DATE, newVetSpecialtyDTO.getCertificationDate());
        assertEquals(YEARS_EXPERIENCE, newVetSpecialtyDTO.getYearsExperience());
        assertEquals(IS_PRIMARY, newVetSpecialtyDTO.isPrimary());
    }


}