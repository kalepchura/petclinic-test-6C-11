package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import org.springframework.stereotype.Component;

@Component
public class VetSpecialtyMapper {

    public VetSpecialty mapToEntity(VetSpecialtyDTO dto) {
        if (dto == null) return null;

        VetSpecialty entity = new VetSpecialty();
        // El ID se establecerá cuando se seteen Vet y Specialty en el servicio
        entity.setCertificationDate(dto.getCertificationDate());
        entity.setYearsExperience(dto.getYearsExperience());
        entity.setIsPrimary(dto.getIsPrimary());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    public VetSpecialtyDTO mapToDto(VetSpecialty entity) {
        if (entity == null) return null;

        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        if (entity.getVet() != null) {
            dto.setVetId(entity.getVet().getId());
        }
        if (entity.getSpecialty() != null) {
            dto.setSpecialtyId(entity.getSpecialty().getId());
        }
        dto.setCertificationDate(entity.getCertificationDate());
        dto.setYearsExperience(entity.getYearsExperience());
        dto.setIsPrimary(entity.getIsPrimary());
        dto.setNotes(entity.getNotes());

        return dto;
    }
}