package com.tecsup.petclinic.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VetSpecialtyId implements Serializable {

    private Integer vetId;
    private Integer specialtyId;

    // Constructores
    public VetSpecialtyId() {}

    public VetSpecialtyId(Integer vetId, Integer specialtyId) {
        this.vetId = vetId;
        this.specialtyId = specialtyId;
    }

    // Getters y Setters
    public Integer getVetId() { return vetId; }
    public void setVetId(Integer vetId) { this.vetId = vetId; }
    public Integer getSpecialtyId() { return specialtyId; }
    public void setSpecialtyId(Integer specialtyId) { this.specialtyId = specialtyId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VetSpecialtyId that = (VetSpecialtyId) o;
        return Objects.equals(vetId, that.vetId) && Objects.equals(specialtyId, that.specialtyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vetId, specialtyId);
    }

    @Override
    public String toString() {
        return "VetSpecialtyId{" + "vetId=" + vetId + ", specialtyId=" + specialtyId + '}';
    }
}