package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VetSpecialtyService {

    private final VetSpecialtyRepository repository;

    public VetSpecialtyService(VetSpecialtyRepository repository) {
        this.repository = repository;
    }

    public VetSpecialty asignarEspecialidad(VetSpecialty vs) {
        return repository.save(vs);
    }

    public List<VetSpecialty> buscarPorVeterinario(Integer vetId) {
        return repository.findByVetId(vetId);
    }

    public List<VetSpecialty> buscarPorEspecialidad(Integer specialtyId) {
        return repository.findBySpecialtyId(specialtyId);
    }

    public void eliminarRelacion(VetSpecialty vs) {
        repository.deleteById(new VetSpecialtyId(vs.getVetId(), vs.getSpecialtyId()));
    }
}