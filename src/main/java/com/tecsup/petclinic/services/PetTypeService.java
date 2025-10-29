package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import java.util.List;

public interface PetTypeService {
    PetTypeDTO create(PetTypeDTO petTypeDTO);
    PetTypeDTO update(PetTypeDTO petTypeDTO);
    void delete(Integer id) throws PetTypeNotFoundException;
    PetTypeDTO findById(Integer id) throws PetTypeNotFoundException;
    List<PetTypeDTO> findByName(String name);
    List<PetTypeDTO> findByActive(Boolean active);
    List<PetTypeDTO> findByCareLevel(String careLevel);
    List<PetTypeDTO> findAll();
}