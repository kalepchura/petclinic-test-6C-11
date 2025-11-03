package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testFindVetById() {
        String EXPECTED_FIRST_NAME = "James";
        String EXPECTED_LAST_NAME = "Carter";
        Integer ID = 1;

        VetDTO vet = null;
        try {
            vet = vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(EXPECTED_FIRST_NAME, vet.getFirstName());
        assertEquals(EXPECTED_LAST_NAME, vet.getLastName());
    }

    @Test
    public void testFindVetByLastName() {
        String FIND_LAST_NAME = "Leary";
        int SIZE_EXPECTED = 1;

        List<VetDTO> vets = vetService.findByLastName(FIND_LAST_NAME);

        assertEquals(SIZE_EXPECTED, vets.size());
    }

    @Test
    public void testCreateVet() {
        String FIRST_NAME = "Carlos";
        String LAST_NAME = "Mendoza";
        String EMAIL = "carlos.mendoza@petclinic.com";
        String PHONE = "987654321";

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(true)
                .build();

        VetDTO newVetDTO = vetService.create(vetDTO);

        log.info("VET CREATED: " + newVetDTO.toString());

        assertNotNull(newVetDTO.getId());
        assertEquals(FIRST_NAME, newVetDTO.getFirstName());
        assertEquals(LAST_NAME, newVetDTO.getLastName());
        assertEquals(EMAIL, newVetDTO.getEmail());
    }

    @Test
    public void testUpdateVet() {
        String FIRST_NAME = "Ana";
        String LAST_NAME = "Torres";
        String UP_PHONE = "912345678";

        // Crear
        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email("ana.torres@petclinic.com")
                .phone("987654321")
                .active(true)
                .build();

        VetDTO vetDTOCreated = vetService.create(vetDTO);

        // Actualizar
        vetDTOCreated.setPhone(UP_PHONE);
        VetDTO vetDTOUpdated = vetService.update(vetDTOCreated);

        assertEquals(UP_PHONE, vetDTOUpdated.getPhone());
    }

    @Test
    public void testDeleteVet() {
        String FIRST_NAME = "Temporal";
        String LAST_NAME = "Vet";

        // Crear
        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email("temp@petclinic.com")
                .phone("999999999")
                .active(true)
                .build();

        VetDTO newVetDTO = vetService.create(vetDTO);

        // Eliminar
        try {
            vetService.delete(newVetDTO.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // Verificar que no existe
        try {
            vetService.findById(newVetDTO.getId());
            assertTrue(false);
        } catch (VetNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testFindAllActiveVets() {
        List<VetDTO> vets = vetService.findByActive(true);

        log.info("Active vets found: " + vets.size());

        assertTrue(vets.size() >= 5); // Según data.sql hay 5 activos
    }

    @Test
    public void testFindAllVets() {
        int MIN_EXPECTED = 6; // Según data.sql hay 6 vets en total

        List<VetDTO> vets = vetService.findAll();

        log.info("Total vets found: " + vets.size());

        assertTrue(vets.size() >= MIN_EXPECTED);
    }
}