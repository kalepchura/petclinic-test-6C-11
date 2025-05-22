package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PetDTO {

    private Integer id;
    private String name;
    private int typeId;
    private int ownerId;
    private LocalDate birthDate;
}
