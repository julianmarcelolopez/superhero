package com.jlopez.w2m.superhero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlopez.w2m.superhero.dto.SuperHeroResponse;
import com.jlopez.w2m.superhero.mapper.SuperHeroMapper;
import com.jlopez.w2m.superhero.repository.ISuperHeroRepository;
import com.jlopez.w2m.superhero.service.ISuperHeroService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuperHeroServiceImpl implements ISuperHeroService {

	@Autowired
	private ISuperHeroRepository superHeroRepository;

	public void setSuperHeroRepository(ISuperHeroRepository superHeroRepository) {
		this.superHeroRepository = superHeroRepository;
	}

	@Override
	public List<SuperHeroResponse> findAll() throws Exception {
		log.info("Find All SuperHeros...");
		return superHeroRepository.findAll().stream().map(SuperHeroMapper::superHeroToResponse)
				.collect(Collectors.toList());
	}

}
