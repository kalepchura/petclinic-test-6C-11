package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {

    public Visit mapToEntity(VisitDTO dto) {
        if (dto == null) return null;
        return new Visit(
                dto.getId(),
                dto.getPetId(),
                dto.getVetId(),
                dto.getVisitDate(),
                dto.getDescription(),
                dto.getCost()
        );
    }

    public VisitDTO mapToDto(Visit entity) {
        if (entity == null) return null;
        return new VisitDTO(
                entity.getId(),
                entity.getPetId(),
                entity.getVetId(),
                entity.getVisitDate(),
                entity.getDescription(),
                entity.getCost()
        );
    }
}