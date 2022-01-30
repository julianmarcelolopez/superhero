package com.jlopez.w2m.superhero.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jlopez.w2m.superhero.dto.SuperHeroRequest;
import com.jlopez.w2m.superhero.dto.SuperHeroResponse;
import com.jlopez.w2m.superhero.entity.SuperHero;
import com.jlopez.w2m.superhero.exception.custom.ExistingRecordCustomException;
import com.jlopez.w2m.superhero.exception.custom.NotFoundCustomException;
import com.jlopez.w2m.superhero.repository.ISuperHeroRepository;
import com.jlopez.w2m.superhero.service.ISuperHeroService;
import com.jlopez.w2m.superhero.mapper.SuperHeroMapper;

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
		List<SuperHero> superHeroList = superHeroRepository.findAll();
		if (superHeroList.isEmpty())
			throw new NotFoundCustomException("");

		return superHeroList.stream().map(SuperHeroMapper::superHeroToResponse).collect(Collectors.toList());
	}

	@Override
	public SuperHeroResponse findById(Integer id) throws Exception {
		log.info("Find By Id...");
		return SuperHeroMapper.superHeroToResponse(this.getById(id));
	}

	@Override
	@Cacheable(cacheNames = "superhero", key = "#name")
	public List<SuperHeroResponse> findByName(String name) throws Exception {
		log.info("Find By Name: " + name);
		List<SuperHero> superHeroList = superHeroRepository.findByNameContainingIgnoreCase(name);
		if (superHeroList.isEmpty())
			throw new NotFoundCustomException("Name: ".concat(name));
		return superHeroList.stream().map(SuperHeroMapper::superHeroToResponse).collect(Collectors.toList());
	}

	@Override
	@CacheEvict(value = "superhero", allEntries = true)
	public SuperHeroResponse saveSuperHero(SuperHeroRequest superHeroRequest) throws Exception {
		SuperHero superHero = superHeroRepository.findByName(superHeroRequest.getName());
		if (superHero != null)
			throw new ExistingRecordCustomException(superHeroRequest.getName());

		SuperHero superHeroToSave = SuperHeroMapper.superHeroRequestToEntity(superHeroRequest);
		superHeroToSave.setActive(true);
		SuperHeroResponse superHeroResponse = SuperHeroMapper
				.superHeroToResponse(superHeroRepository.save(superHeroToSave));
		log.info("CACHING SAVE: ");
		return superHeroResponse;
	}

	@Override
	@CacheEvict(value = "superhero", allEntries = true)
	public SuperHeroResponse updateSuperHero(Integer id, SuperHeroRequest superHeroRequest) throws Exception {
		SuperHero superHero = this.getById(id);

		superHero.setActive(true);
		superHero.setName(superHeroRequest.getName());
		superHero = superHeroRepository.save(superHero);

		return SuperHeroMapper.superHeroToResponse(superHero);
	}

	@Override
	@CacheEvict(value = "superhero", allEntries = true)
	public void deleteSuperHeroById(Integer id) throws Exception {
		SuperHero superHero = this.getById(id);
		superHero.setActive(false);

		superHeroRepository.save(superHero);
	}

	private SuperHero getById(Integer id) throws Exception {
		log.info("Get By Id...");
		Optional<SuperHero> superHeroOp = superHeroRepository.findById(id);
		if (!superHeroOp.isPresent()) {
			throw new NotFoundCustomException("Id: ".concat(id.toString()));
		}
		return superHeroOp.get();
	}
}
