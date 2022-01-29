package com.jlopez.w2m.superhero.mapper;

import com.jlopez.w2m.superhero.dto.SuperHeroRequest;
import com.jlopez.w2m.superhero.dto.SuperHeroResponse;
import com.jlopez.w2m.superhero.entity.SuperHero;

public class SuperHeroMapper {

	private SuperHeroMapper() {
	}

	public static SuperHeroResponse superHeroToResponse(SuperHero superHero) {
		return SuperHeroResponse.builder().id(superHero.getId()).name(superHero.getName()).build();
	}

	public static SuperHero superHeroRequestToEntity(SuperHeroRequest superHeroRequest) {
		return SuperHero.builder().name(superHeroRequest.getName()).build();
	}
}

