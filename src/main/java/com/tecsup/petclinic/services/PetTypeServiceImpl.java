package com.tecsup.petclinic.services;


import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.mappers.PetTypeMapper;
import com.tecsup.petclinic.repositories.PetTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PetTypeServiceImpl implements PetTypeService {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private PetTypeMapper petTypeMapper;

    @Override
    public PetTypeDTO create(PetTypeDTO petTypeDTO) {
        log.info("Creating PetType: {}", petTypeDTO);
        PetType newPetType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));
        return petTypeMapper.mapToDto(newPetType);
    }

    @Override
    public PetTypeDTO update(PetTypeDTO petTypeDTO) {
        log.info("Updating PetType: {}", petTypeDTO);
        PetType updatedPetType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));
        return petTypeMapper.mapToDto(updatedPetType);
    }

    @Override
    public void delete(Integer id) throws PetTypeNotFoundException {
        log.info("Deleting PetType with id: {}", id);
        PetTypeDTO petType = findById(id);
        petTypeRepository.delete(petTypeMapper.mapToEntity(petType));
    }

    @Override
    public PetTypeDTO findById(Integer id) throws PetTypeNotFoundException {
        log.info("Finding PetType by id: {}", id);
        Optional<PetType> petType = petTypeRepository.findById(id);
        if (!petType.isPresent())
            throw new PetTypeNotFoundException("PetType not found with id: " + id);
        return petTypeMapper.mapToDto(petType.get());
    }

    @Override
    public List<PetTypeDTO> findByName(String name) {
        log.info("Finding PetTypes by name: {}", name);
        List<PetType> petTypes = petTypeRepository.findByName(name);
        return petTypes.stream()
                .map(petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetTypeDTO> findByActive(Boolean active) {
        log.info("Finding PetTypes by active status: {}", active);
        List<PetType> petTypes = petTypeRepository.findByActive(active);
        return petTypes.stream()
                .map(petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetTypeDTO> findByCareLevel(String careLevel) {
        log.info("Finding PetTypes by care level: {}", careLevel);
        List<PetType> petTypes = petTypeRepository.findByCareLevel(careLevel);
        return petTypes.stream()
                .map(petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetTypeDTO> findAll() {
        log.info("Finding all PetTypes");
        return petTypeRepository.findAll().stream()
                .map(petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }
}