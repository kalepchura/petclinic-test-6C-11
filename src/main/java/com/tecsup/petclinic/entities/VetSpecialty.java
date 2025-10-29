package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vet_specialties")
public class VetSpecialty {

    // USAMOS CLAVE PRIMARIA COMPUESTA - SIN @Id individual
    @EmbeddedId
    private VetSpecialtyId id;

    @ManyToOne
    @MapsId("vetId")  // Esto mapea a la parte "vetId" del embedded ID
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne
    @MapsId("specialtyId")  // Esto mapea a la parte "specialtyId" del embedded ID
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Column(name = "certification_date")
    private LocalDate certificationDate;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    private String notes;

    // Constructores
    public VetSpecialty() {}

    public VetSpecialty(Vet vet, Specialty specialty, LocalDate certificationDate,
                        Integer yearsExperience, Boolean isPrimary, String notes) {
        this.vet = vet;
        this.specialty = specialty;
        this.certificationDate = certificationDate;
        this.yearsExperience = yearsExperience;
        this.isPrimary = isPrimary;
        this.notes = notes;
        this.id = new VetSpecialtyId(vet.getId(), specialty.getId());
    }

    // Getters y Setters
    public VetSpecialtyId getId() { return id; }
    public void setId(VetSpecialtyId id) { this.id = id; }
    public Vet getVet() { return vet; }
    public void setVet(Vet vet) { this.vet = vet; }
    public Specialty getSpecialty() { return specialty; }
    public void setSpecialty(Specialty specialty) { this.specialty = specialty; }
    public LocalDate getCertificationDate() { return certificationDate; }
    public void setCertificationDate(LocalDate certificationDate) { this.certificationDate = certificationDate; }
    public Integer getYearsExperience() { return yearsExperience; }
    public void setYearsExperience(Integer yearsExperience) { this.yearsExperience = yearsExperience; }
    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "VetSpecialty [id=" + id + ", vet=" + vet + ", specialty=" + specialty +
                ", certificationDate=" + certificationDate + ", yearsExperience=" + yearsExperience +
                ", isPrimary=" + isPrimary + ", notes=" + notes + "]";
    }
}