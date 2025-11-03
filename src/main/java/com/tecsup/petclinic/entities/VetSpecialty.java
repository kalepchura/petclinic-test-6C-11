package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vet_specialties")
@IdClass(VetSpecialtyId.class)
public class VetSpecialty {

    @Id
    private Integer vetId;

    @Id
    private Integer specialtyId;

    private LocalDate certificationDate;
    private int yearsExperience;
    private boolean isPrimary;

    public VetSpecialty() {}

    public VetSpecialty(Integer vetId, Integer specialtyId, LocalDate certificationDate,
                        int yearsExperience, boolean isPrimary) {
        this.vetId = vetId;
        this.specialtyId = specialtyId;
        this.certificationDate = certificationDate;
        this.yearsExperience = yearsExperience;
        this.isPrimary = isPrimary;
    }

    public Integer getVetId() { return vetId; }
    public Integer getSpecialtyId() { return specialtyId; }
    public LocalDate getCertificationDate() { return certificationDate; }
    public int getYearsExperience() { return yearsExperience; }
    public boolean isPrimary() { return isPrimary; }

    public void setVetId(Integer vetId) { this.vetId = vetId; }
    public void setSpecialtyId(Integer specialtyId) { this.specialtyId = specialtyId; }
    public void setCertificationDate(LocalDate certificationDate) { this.certificationDate = certificationDate; }
    public void setYearsExperience(int yearsExperience) { this.yearsExperience = yearsExperience; }
    public void setPrimary(boolean primary) { isPrimary = primary; }
}

