package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository repository;

    public VetSpecialtyServiceImpl(VetSpecialtyRepository repository) {
        this.repository = repository;
    }

    @Override
    public VetSpecialtyDTO create(VetSpecialtyDTO vetSpecialtyDTO) {
        VetSpecialty vetSpecialty = new VetSpecialty(
                vetSpecialtyDTO.getVetId(),
                vetSpecialtyDTO.getSpecialtyId(),
                vetSpecialtyDTO.getCertificationDate(),
                vetSpecialtyDTO.getYearsExperience(),
                vetSpecialtyDTO.isPrimary()
        );

        VetSpecialty savedVetSpecialty = repository.save(vetSpecialty);

        return new VetSpecialtyDTO(
                savedVetSpecialty.getVetId(),
                savedVetSpecialty.getSpecialtyId(),
                savedVetSpecialty.getCertificationDate(),
                savedVetSpecialty.getYearsExperience(),
                savedVetSpecialty.isPrimary()
        );
    }

    @Override
    public List<VetSpecialtyDTO> findByVetId(Integer vetId) {
        List<VetSpecialty> vetSpecialties = repository.findByVetId(vetId);

        return vetSpecialties.stream()
                .map(vs -> new VetSpecialtyDTO(
                        vs.getVetId(),
                        vs.getSpecialtyId(),
                        vs.getCertificationDate(),
                        vs.getYearsExperience(),
                        vs.isPrimary()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findBySpecialtyId(Integer specialtyId) {
        List<VetSpecialty> vetSpecialties = repository.findBySpecialtyId(specialtyId);

        return vetSpecialties.stream()
                .map(vs -> new VetSpecialtyDTO(
                        vs.getVetId(),
                        vs.getSpecialtyId(),
                        vs.getCertificationDate(),
                        vs.getYearsExperience(),
                        vs.isPrimary()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(VetSpecialtyDTO vetSpecialtyDTO) {
        VetSpecialtyId id = new VetSpecialtyId(vetSpecialtyDTO.getVetId(), vetSpecialtyDTO.getSpecialtyId());
        repository.deleteById(id);
    }

    @Override
    public List<VetSpecialtyDTO> findAll() {
        List<VetSpecialty> vetSpecialties = repository.findAll();

        return vetSpecialties.stream()
                .map(vs -> new VetSpecialtyDTO(
                        vs.getVetId(),
                        vs.getSpecialtyId(),
                        vs.getCertificationDate(),
                        vs.getYearsExperience(),
                        vs.isPrimary()
                ))
                .collect(Collectors.toList());
    }
}