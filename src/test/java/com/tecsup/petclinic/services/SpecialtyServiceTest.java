package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class SpecialtyServiceTest {

	@Autowired
	private SpecialtyService specialtyService;

	@Test
	public void shouldCreateSpecialty() {
		Specialty specialty = new Specialty("Dermatology");
		Specialty saved = specialtyService.save(specialty);
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getName()).isEqualTo("Dermatology");
	}

	@Test
	public void shouldFindSpecialtyById() {
		Specialty specialty = new Specialty("Surgery");
		Specialty saved = specialtyService.save(specialty);
		Specialty found = specialtyService.findById(saved.getId());
		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("Surgery");
	}

	@Test
	public void shouldUpdateSpecialty() {
		Specialty specialty = new Specialty("Dentistry");
		Specialty saved = specialtyService.save(specialty);

		saved.setName("Updated Dentistry");
		Specialty updated = specialtyService.save(saved);

		assertThat(updated.getName()).isEqualTo("Updated Dentistry");
	}

	@Test
	public void shouldDeleteSpecialty() {
		Specialty specialty = new Specialty("Oncology");
		Specialty saved = specialtyService.save(specialty);
		specialtyService.deleteById(saved.getId());

		Specialty deleted = specialtyService.findById(saved.getId());
		assertThat(deleted).isNull();
	}
}