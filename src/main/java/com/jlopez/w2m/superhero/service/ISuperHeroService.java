package com.jlopez.w2m.superhero.service;

import java.util.List;

import com.jlopez.w2m.superhero.dto.SuperHeroRequest;
import com.jlopez.w2m.superhero.dto.SuperHeroResponse;


public interface ISuperHeroService {

	public List<SuperHeroResponse> findAll() throws Exception;

	public SuperHeroResponse findById(Integer id) throws Exception;

	public List<SuperHeroResponse> findByName(String name) throws Exception;

	public SuperHeroResponse saveSuperHero(SuperHeroRequest superHeroRequest) throws Exception;

	public SuperHeroResponse updateSuperHero(Integer id, SuperHeroRequest superHeroRequest) throws Exception;

	public void deleteSuperHeroById(Integer id) throws Exception;

}
