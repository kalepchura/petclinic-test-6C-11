package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;

import java.util.List;

public interface OwnerService {
    Owner save(Owner owner);
    Owner findById(Integer id);
    List<Owner> findAll();
    void deleteById(Integer id);
}