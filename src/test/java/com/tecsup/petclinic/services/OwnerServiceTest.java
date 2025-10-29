package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	@Test
	void shouldCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Mayela");
		owner.setLastName("Gómez");
		owner.setAddress("Av. Siempre Viva 123");
		owner.setCity("Lima");
		owner.setTelephone("987654321");

		Owner saved = ownerService.save(owner);
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getFirstName()).isEqualTo("Mayela");
	}

	@Test
	void shouldFindOwnerById() {
		Owner owner = new Owner();
		owner.setFirstName("Carlos");
		owner.setLastName("Díaz");
		Owner saved = ownerService.save(owner);

		Owner found = ownerService.findById(saved.getId());
		assertThat(found).isNotNull();
		assertThat(found.getLastName()).isEqualTo("Díaz");
	}

	@Test
	void shouldUpdateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Ana");
		owner.setTelephone("111111111");
		Owner saved = ownerService.save(owner);

		saved.setTelephone("222222222");
		Owner updated = ownerService.save(saved);

		assertThat(updated.getTelephone()).isEqualTo("222222222");
	}

	@Test
	void shouldDeleteOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Luis");
		Owner saved = ownerService.save(owner);

		ownerService.deleteById(saved.getId());

		Owner deleted = ownerService.findById(saved.getId());
		assertThat(deleted).isNull();
	}

	@Test
	void shouldFindAllOwners() {
		// Limpiar antes
		List<Owner> all = ownerService.findAll();
		for (Owner o : all) {
			ownerService.deleteById(o.getId());
		}

		ownerService.save(new Owner("María", "López", "Calle Falsa 123", "Madrid", "123456789"));
		ownerService.save(new Owner("Pedro", "Ruiz", "Calle Real 456", "Barcelona", "987654321"));

		List<Owner> owners = ownerService.findAll();
		assertThat(owners).hasSize(2);
	}
}