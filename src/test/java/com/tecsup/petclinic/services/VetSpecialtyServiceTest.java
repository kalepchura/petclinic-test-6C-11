package com.tecsup.petclinic.services;


import com.tecsup.petclinic.entities.VetSpecialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VetSpecialtyServiceTest {

    private VetSpecialtyService service;

    @Autowired
    private com.tecsup.petclinic.repositories.VetSpecialtyRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        service = new VetSpecialtyService(repository);
    }

    @Test
    void pruebaAsignarEspecialidad() {
        VetSpecialty vs = new VetSpecialty(1, 1, LocalDate.now(), 5, true);
        VetSpecialty creado = service.asignarEspecialidad(vs);
        assertNotNull(creado);
    }

    @Test
    void pruebaBuscarPorVeterinario() {
        service.asignarEspecialidad(new VetSpecialty(1, 1, LocalDate.now(), 5, true));
        service.asignarEspecialidad(new VetSpecialty(1, 2, LocalDate.now(), 3, false));
        List<VetSpecialty> lista = service.buscarPorVeterinario(1);
        assertEquals(2, lista.size());
    }

    @Test
    void pruebaBuscarPorEspecialidad() {
        service.asignarEspecialidad(new VetSpecialty(1, 1, LocalDate.now(), 5, true));
        service.asignarEspecialidad(new VetSpecialty(2, 1, LocalDate.now(), 2, false));
        List<VetSpecialty> lista = service.buscarPorEspecialidad(1);
        assertEquals(2, lista.size());
    }

    @Test
    void pruebaEliminarRelacion() {
        VetSpecialty vs = new VetSpecialty(1, 1, LocalDate.now(), 5, true);
        service.asignarEspecialidad(vs);
        service.eliminarRelacion(vs);
        List<VetSpecialty> lista = service.buscarPorVeterinario(1);
        assertEquals(0, lista.size());
    }
}
