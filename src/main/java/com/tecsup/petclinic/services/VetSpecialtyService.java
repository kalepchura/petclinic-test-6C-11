package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import java.util.List;

public interface VetSpecialtyService {
    VetSpecialtyDTO create(VetSpecialtyDTO vetSpecialtyDTO);
    List<VetSpecialtyDTO> findByVetId(Integer vetId);
    List<VetSpecialtyDTO> findBySpecialtyId(Integer specialtyId);
    void delete(VetSpecialtyDTO vetSpecialtyDTO);
    List<VetSpecialtyDTO> findAll();
}