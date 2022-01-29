package com.jlopez.w2m.superhero;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jlopez.w2m.superhero.controller.SuperHeroController;
import com.jlopez.w2m.superhero.dto.SuperHeroResponse;
import com.jlopez.w2m.superhero.entity.SuperHero;
import com.jlopez.w2m.superhero.mapper.SuperHeroMapper;
import com.jlopez.w2m.superhero.repository.ISuperHeroRepository;
import com.jlopez.w2m.superhero.service.ISuperHeroService;

@SpringBootTest
class SuperheroControllerTest {

	@InjectMocks
	private SuperHeroController superHeroController;

	@MockBean
	private ISuperHeroService superHeroService;

	@Mock
	private ISuperHeroRepository repository;

	private List<SuperHeroResponse> superHeroResponseList;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(superHeroController).build();

		List<SuperHero> superHeroList = new ArrayList<>();
		superHeroList.add(new SuperHero(1, "Spiderman", true));
		superHeroList.add(new SuperHero(2, "Superman", true));
		superHeroList.add(new SuperHero(3, "Batman", true));

		superHeroResponseList = superHeroList.stream().map(SuperHeroMapper::superHeroToResponse)
				.collect(Collectors.toList());
	}

	@Test
	void shouldFetchAllSuperHeros_success() throws Exception {

		when(superHeroService.findAll()).thenReturn(superHeroResponseList);

		this.mockMvc.perform(get("/api/superhero/")).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
	}

}
