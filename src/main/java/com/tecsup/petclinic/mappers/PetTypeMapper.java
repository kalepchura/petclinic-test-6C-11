package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import org.springframework.stereotype.Component;

@Component
public class PetTypeMapper {

    public PetType mapToEntity(PetTypeDTO dto) {
        if (dto == null) return null;
        return new PetType(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getActive(),
                dto.getSizeCategory(),
                dto.getAverageLifespan(),
                dto.getCareLevel()
        );
    }

    public PetTypeDTO mapToDto(PetType entity) {
        if (entity == null) return null;
        return PetTypeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .sizeCategory(entity.getSizeCategory())
                .averageLifespan(entity.getAverageLifespan())
                .careLevel(entity.getCareLevel())
                .build();
    }
}