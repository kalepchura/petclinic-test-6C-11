package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PetTypeServiceTest {

    @Autowired
    private PetTypeService petTypeService;

    @Test
    void shouldFindPetTypeById() {
        String EXPECTED_NAME = "cat";
        Integer ID = 1;

        PetTypeDTO petType = null;
        try {
            petType = petTypeService.findById(ID);
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("PetType found: {}", petType);
        assertEquals(EXPECTED_NAME, petType.getName());
        assertTrue(petType.getActive());
    }

    @Test
    void shouldFindPetTypesByName() {
        String FIND_NAME = "dog";
        int SIZE_EXPECTED = 1;

        List<PetTypeDTO> petTypes = petTypeService.findByName(FIND_NAME);

        log.info("PetTypes found: {}", petTypes.size());
        assertEquals(SIZE_EXPECTED, petTypes.size());
        assertEquals(FIND_NAME, petTypes.get(0).getName());
    }

    @Test
    void shouldFindActivePetTypes() {
        List<PetTypeDTO> activePetTypes = petTypeService.findByActive(true);

        log.info("Active PetTypes found: {}", activePetTypes.size());
        assertFalse(activePetTypes.isEmpty());
        assertTrue(activePetTypes.size() >= 7); // Según data.sql hay 7 activos
    }

    @Test
    void shouldFindPetTypesByCareLevel() {
        String CARE_LEVEL = "high";
        int MIN_EXPECTED = 2; // lizard y snake

        List<PetTypeDTO> petTypes = petTypeService.findByCareLevel(CARE_LEVEL);

        log.info("PetTypes with care level '{}' found: {}", CARE_LEVEL, petTypes.size());
        assertTrue(petTypes.size() >= MIN_EXPECTED);
    }

    @Test
    void shouldCreatePetType() {
        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name("fish")
                .description("Aquatic animal")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(5)
                .careLevel("medium")
                .build();

        PetTypeDTO createdPetType = petTypeService.create(petTypeDTO);

        log.info("PetType created: {}", createdPetType);
        assertNotNull(createdPetType.getId());
        assertEquals("fish", createdPetType.getName());
        assertEquals("Aquatic animal", createdPetType.getDescription());
    }

    @Test
    void shouldUpdatePetType() throws PetTypeNotFoundException {
        // Crear un nuevo tipo primero
        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name("test")
                .description("test description")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(5)
                .careLevel("low")
                .build();
        PetTypeDTO createdPetType = petTypeService.create(petTypeDTO);

        // Actualizar
        createdPetType.setDescription("updated description");
        createdPetType.setCareLevel("high");
        PetTypeDTO updatedPetType = petTypeService.update(createdPetType);

        log.info("PetType updated: {}", updatedPetType);
        assertEquals("updated description", updatedPetType.getDescription());
        assertEquals("high", updatedPetType.getCareLevel());
    }

    @Test
    void shouldDeletePetType() {
        // Crear un tipo temporal
        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name("temporary")
                .description("temporary type")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(3)
                .careLevel("low")
                .build();
        PetTypeDTO createdPetType = petTypeService.create(petTypeDTO);

        // Eliminar
        assertDoesNotThrow(() -> {
            petTypeService.delete(createdPetType.getId());
        });

        // Verificar que fue eliminado
        assertThrows(PetTypeNotFoundException.class, () -> {
            petTypeService.findById(createdPetType.getId());
        });
    }

    @Test
    void shouldFindAllPetTypes() {
        int MIN_EXPECTED = 8; // Según data.sql hay 8 tipos

        List<PetTypeDTO> allPetTypes = petTypeService.findAll();

        log.info("Total PetTypes found: {}", allPetTypes.size());
        assertTrue(allPetTypes.size() >= MIN_EXPECTED);
    }
}
