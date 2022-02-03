package com.jlopez.w2m.superhero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jlopez.w2m.superhero.dto.SuperHeroResponse;
import com.jlopez.w2m.superhero.entity.SuperHero;
import com.jlopez.w2m.superhero.exception.custom.NotFoundCustomException;
import com.jlopez.w2m.superhero.repository.ISuperHeroRepository;
import com.jlopez.w2m.superhero.service.impl.SuperHeroServiceImpl;

public class SuperheroServiceTest {

	@InjectMocks
	private SuperHeroServiceImpl superHeroService;

	@Mock
	private ISuperHeroRepository repository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void whenFindAllActiveSuperHeros_returnAllSuperHerosTest() throws Exception {
		Optional<SuperHero> superHeroA = Optional.ofNullable(new SuperHero());
		superHeroA.get().setId(1);
		superHeroA.get().setName("Spiderman");
		superHeroA.get().setOwner("Marvel");
		superHeroA.get().setActive(true);

		Optional<SuperHero> superHeroB = Optional.ofNullable(new SuperHero());
		superHeroB.get().setId(2);
		superHeroB.get().setName("Hulk");
		superHeroB.get().setOwner("Marvel");
		superHeroB.get().setActive(true);

		Optional<SuperHero> superHeroC = Optional.ofNullable(new SuperHero());
		superHeroC.get().setId(3);
		superHeroC.get().setName("Superman");
		superHeroC.get().setOwner("DC");
		superHeroC.get().setActive(true);

		List<SuperHero> superHeroList = new ArrayList<>();
		superHeroList.add(superHeroA.get());
		superHeroList.add(superHeroB.get());
		superHeroList.add(superHeroC.get());

		repository.save(superHeroA.get());
		when(repository.findAllByActive(true)).thenReturn(superHeroList);

		List<SuperHeroResponse> expected = superHeroService.findAll();

		assertEquals(expected.size(), superHeroList.size());
	}

	@Test
	void whenFindAllActiveEmptySuperHeros_returnEmptyTest() throws Exception {
		List<SuperHero> emptyList = new ArrayList<>();
		when(repository.findAllByActive(true)).thenReturn(emptyList);
		assertThrows(NotFoundCustomException.class, () -> superHeroService.findAll());
	}

	@Test
	void whenFindByIdAndActiveSuperHero_returnSuperHeroTest() throws Exception {
		Integer inputId = 1;

		Optional<SuperHero> heroEntity = Optional.ofNullable(new SuperHero());
		heroEntity.get().setId(inputId);
		heroEntity.get().setName("Spiderman");
		heroEntity.get().setOwner("Marvel");
		heroEntity.get().setActive(true);

		when(repository.findByIdAndActive(inputId, true)).thenReturn(heroEntity);

		String expectedSuperHero = "Spiderman";
		assertEquals(superHeroService.findById(inputId).getName(), expectedSuperHero);
	}

	@Test
	void whenFindByIdEmpty_returnNotFoundTest() throws Exception {
		Integer inputId = 1;
		when(repository.findByIdAndActive(inputId, true)).thenReturn(Optional.empty());
		assertThrows(NotFoundCustomException.class, () -> superHeroService.findById(inputId));
	}
}