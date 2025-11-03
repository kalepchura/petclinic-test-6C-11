package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;

import java.util.List;

public interface SpecialtyService {
    Specialty save(Specialty specialty);
    Specialty findById(Integer id);
    List<Specialty> findAll();
    void deleteById(Integer id);
}