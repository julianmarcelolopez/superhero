package com.jlopez.w2m.superhero.service;

import java.util.List;

import com.jlopez.w2m.superhero.dto.SuperHeroResponse;

public interface ISuperHeroService {

	public List<SuperHeroResponse> findAll() throws Exception;

}
