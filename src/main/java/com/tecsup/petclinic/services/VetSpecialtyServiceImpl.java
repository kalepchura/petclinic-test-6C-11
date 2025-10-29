package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    @Autowired
    private VetSpecialtyRepository vetSpecialtyRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private VetSpecialtyMapper vetSpecialtyMapper;

    @Override
    public VetSpecialtyDTO create(VetSpecialtyDTO vetSpecialtyDTO) {
        log.info("Creating VetSpecialty: {}", vetSpecialtyDTO);

        try {
            // Buscar Vet y Specialty
            Vet vet = vetRepository.findById(vetSpecialtyDTO.getVetId())
                    .orElseThrow(() -> new RuntimeException("Vet not found with id: " + vetSpecialtyDTO.getVetId()));

            Specialty specialty = specialtyRepository.findById(vetSpecialtyDTO.getSpecialtyId())
                    .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + vetSpecialtyDTO.getSpecialtyId()));

            // Verificar si ya existe esta relación
            VetSpecialtyId id = new VetSpecialtyId(vetSpecialtyDTO.getVetId(), vetSpecialtyDTO.getSpecialtyId());
            if (vetSpecialtyRepository.existsById(id)) {
                throw new RuntimeException("VetSpecialty relationship already exists");
            }

            // Crear entidad
            VetSpecialty vetSpecialty = vetSpecialtyMapper.mapToEntity(vetSpecialtyDTO);
            vetSpecialty.setVet(vet);
            vetSpecialty.setSpecialty(specialty);
            vetSpecialty.setId(id);

            VetSpecialty savedVetSpecialty = vetSpecialtyRepository.save(vetSpecialty);
            return vetSpecialtyMapper.mapToDto(savedVetSpecialty);

        } catch (Exception e) {
            log.error("Error creating VetSpecialty: {}", e.getMessage());
            throw new RuntimeException("Error creating VetSpecialty: " + e.getMessage());
        }
    }

    @Override
    public VetSpecialtyDTO update(VetSpecialtyDTO vetSpecialtyDTO) throws VetSpecialtyNotFoundException { // AÑADÍ "throws"
        log.info("Updating VetSpecialty: {}", vetSpecialtyDTO);

        try {
            // Buscar la entidad existente
            VetSpecialtyId id = new VetSpecialtyId(vetSpecialtyDTO.getVetId(), vetSpecialtyDTO.getSpecialtyId());
            VetSpecialty existing = vetSpecialtyRepository.findById(id)
                    .orElseThrow(() -> new VetSpecialtyNotFoundException("VetSpecialty not found"));

            // Actualizar campos
            existing.setCertificationDate(vetSpecialtyDTO.getCertificationDate());
            existing.setYearsExperience(vetSpecialtyDTO.getYearsExperience());
            existing.setIsPrimary(vetSpecialtyDTO.getIsPrimary());
            existing.setNotes(vetSpecialtyDTO.getNotes());

            VetSpecialty updatedVetSpecialty = vetSpecialtyRepository.save(existing);
            return vetSpecialtyMapper.mapToDto(updatedVetSpecialty);

        } catch (VetSpecialtyNotFoundException e) {
            log.error("VetSpecialty not found during update: {}", e.getMessage());
            throw e; // Relanzamos la excepción específica
        } catch (Exception e) {
            log.error("Error updating VetSpecialty: {}", e.getMessage());
            throw new RuntimeException("Error updating VetSpecialty: " + e.getMessage());
        }
    }

    @Override
    public void delete(VetSpecialtyDTO vetSpecialtyDTO) throws VetSpecialtyNotFoundException {
        log.info("Deleting VetSpecialty: {}", vetSpecialtyDTO);
        try {
            VetSpecialtyId id = new VetSpecialtyId(vetSpecialtyDTO.getVetId(), vetSpecialtyDTO.getSpecialtyId());
            VetSpecialty vetSpecialty = vetSpecialtyRepository.findById(id)
                    .orElseThrow(() -> new VetSpecialtyNotFoundException("VetSpecialty not found"));
            vetSpecialtyRepository.delete(vetSpecialty);
        } catch (VetSpecialtyNotFoundException e) {
            log.error("VetSpecialty not found during deletion: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error deleting VetSpecialty: {}", e.getMessage());
            throw new RuntimeException("Error deleting VetSpecialty: " + e.getMessage());
        }
    }

    @Override
    public VetSpecialtyDTO findVetSpecialtyByCompositeId(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        log.info("Finding specific VetSpecialty by vetId: {} and specialtyId: {}", vetId, specialtyId);
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        VetSpecialty vetSpecialty = vetSpecialtyRepository.findById(id)
                .orElseThrow(() -> new VetSpecialtyNotFoundException("VetSpecialty not found with vetId: " + vetId + " and specialtyId: " + specialtyId));
        return vetSpecialtyMapper.mapToDto(vetSpecialty);
    }

    @Override
    public List<VetSpecialtyDTO> findByVetId(Integer vetId) {
        log.info("Finding VetSpecialties by vetId: {}", vetId);
        List<VetSpecialty> vetSpecialties = vetSpecialtyRepository.findByVetId(vetId);
        return vetSpecialties.stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findBySpecialtyId(Integer specialtyId) {
        log.info("Finding VetSpecialties by specialtyId: {}", specialtyId);
        List<VetSpecialty> vetSpecialties = vetSpecialtyRepository.findBySpecialtyId(specialtyId);
        return vetSpecialties.stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findByVetIdAndSpecialtyId(Integer vetId, Integer specialtyId) {
        log.info("Finding VetSpecialties by vetId: {} and specialtyId: {}", vetId, specialtyId);
        List<VetSpecialty> vetSpecialties = vetSpecialtyRepository.findByVetIdAndSpecialtyId(vetId, specialtyId);
        return vetSpecialties.stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findByIsPrimary(Boolean isPrimary) {
        log.info("Finding VetSpecialties by isPrimary: {}", isPrimary);
        List<VetSpecialty> vetSpecialties = vetSpecialtyRepository.findByIsPrimary(isPrimary);
        return vetSpecialties.stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findAll() {
        log.info("Finding all VetSpecialties");
        return vetSpecialtyRepository.findAll().stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }
}