package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import java.util.List;

public interface VetSpecialtyService {
    VetSpecialtyDTO create(VetSpecialtyDTO vetSpecialtyDTO);
    VetSpecialtyDTO update(VetSpecialtyDTO vetSpecialtyDTO) throws VetSpecialtyNotFoundException; // AÑADÍ "throws"
    void delete(VetSpecialtyDTO vetSpecialtyDTO) throws VetSpecialtyNotFoundException;

    VetSpecialtyDTO findVetSpecialtyByCompositeId(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    List<VetSpecialtyDTO> findByVetId(Integer vetId);
    List<VetSpecialtyDTO> findBySpecialtyId(Integer specialtyId);
    List<VetSpecialtyDTO> findByVetIdAndSpecialtyId(Integer vetId, Integer specialtyId);
    List<VetSpecialtyDTO> findByIsPrimary(Boolean isPrimary);
    List<VetSpecialtyDTO> findAll();
}