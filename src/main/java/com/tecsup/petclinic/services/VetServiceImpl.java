package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VetServiceImpl implements VetService {

    VetRepository vetRepository;
    VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet newVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(newVet);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet updatedVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(updatedVet);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        VetDTO vet = findById(id);
        vetRepository.delete(vetMapper.mapToEntity(vet));
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (!vet.isPresent())
            throw new VetNotFoundException("Record not found...!");
        return vetMapper.mapToDto(vet.get());
    }

    @Override
    public List<VetDTO> findByLastName(String lastName) {
        List<Vet> vets = vetRepository.findByLastName(lastName);
        return vets.stream()
                .map(vetMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findByActive(Boolean active) {
        List<Vet> vets = vetRepository.findByActive(active);
        return vets.stream()
                .map(vetMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findAll() {
        return vetRepository.findAll().stream()
                .map(vetMapper::mapToDto)
                .collect(Collectors.toList());
    }
}