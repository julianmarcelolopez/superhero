package com.jlopez.w2m.superhero.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jlopez.w2m.superhero.dto.SuperHeroRequest;
import com.jlopez.w2m.superhero.dto.SuperHeroResponse;
import com.jlopez.w2m.superhero.entity.SuperHero;
import com.jlopez.w2m.superhero.exception.custom.BadRequestCustomException;
import com.jlopez.w2m.superhero.exception.custom.ExistingRecordCustomException;
import com.jlopez.w2m.superhero.exception.custom.NotFoundCustomException;
import com.jlopez.w2m.superhero.repository.ISuperHeroRepository;
import com.jlopez.w2m.superhero.service.ISuperHeroService;
import com.jlopez.w2m.superhero.mapper.SuperHeroMapper;
import java.util.function.Predicate;

import lombok.var;
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
		List<SuperHero> superHeroList = superHeroRepository.findAllByActive(true);
		if (superHeroList.isEmpty())
			throw new NotFoundCustomException("");

		// Adding log that returns all superheros with lamba expression - JAVA 11
		// Predicate.not(String::isBlank)
		List<String> nameList = superHeroList.stream().map(x -> x.getName()).filter(Predicate.not(String::isBlank))
				.collect(Collectors.toList());

		var immutableListJava11 = List.of(nameList);
		log.info("SuperHero Names:".concat(immutableListJava11.toString()));

		return superHeroList.stream().map(SuperHeroMapper::superHeroToResponse).collect(Collectors.toList());
	}

	@Override
	public SuperHeroResponse findById(Integer id) throws Exception {
		log.info("Find By Id...".concat(id.toString()));
		return SuperHeroMapper.superHeroToResponse(this.getById(id));
	}

	@Override
	@Cacheable(cacheNames = "superhero", key = "#name")
	public List<SuperHeroResponse> findByName(String name) throws Exception {
		log.info("Find By Name: " + name);

		// JAVA 11: String.isBlank
		if (name.isBlank()) {
			throw new BadRequestCustomException("String is blank");
		}

		List<SuperHero> superHeroList = superHeroRepository.findByNameContainingIgnoreCaseAndActive(name, true);

		if (superHeroList.isEmpty())
			throw new NotFoundCustomException("Name: ".concat(name));

		// Filter getOwner not blank in JAVA 11 Stream with takeWhile accepts predicate
		return superHeroList.stream().takeWhile(s -> !s.getOwner().isBlank()).map(SuperHeroMapper::superHeroToResponse)
				.collect(Collectors.toList());
	}

	@Override
	@CacheEvict(value = "superhero", allEntries = true)
	public SuperHeroResponse saveSuperHero(SuperHeroRequest superHeroRequest) throws Exception {
		log.info("Create SuperHero: ".concat(superHeroRequest.getName()));

		Optional<SuperHero> superHeroOp = superHeroRepository.findByNameAndActive(superHeroRequest.getName(), true);
		if (superHeroOp.isPresent())
			throw new ExistingRecordCustomException(superHeroRequest.getName());

		SuperHero superHeroToSave = SuperHeroMapper.superHeroRequestToEntity(superHeroRequest);
		superHeroToSave.setActive(true);

		SuperHeroResponse superHeroResponse = SuperHeroMapper
				.superHeroToResponse(superHeroRepository.save(superHeroToSave));
		return superHeroResponse;
	}

	@Override
	@CacheEvict(value = "superhero", allEntries = true)
	public SuperHeroResponse updateSuperHero(Integer id, SuperHeroRequest superHeroRequest) throws Exception {
		log.info("Update SuperHero Id: ".concat(id.toString()));
		SuperHero superHero = this.getById(id);

		superHero.setActive(true);
		superHero.setName(superHeroRequest.getName());
		superHero.setOwner(superHeroRequest.getOwner());
		superHero.setUpdatedAt(new Date());

		superHero = superHeroRepository.save(superHero);
		return SuperHeroMapper.superHeroToResponse(superHero);
	}

	@Override
	@CacheEvict(value = "superhero", allEntries = true)
	public void deleteSuperHeroById(Integer id) throws Exception {
		log.info("Delete SuperHero Id: ".concat(id.toString()));
		SuperHero superHero = this.getById(id);
		superHero.setActive(false);

		superHeroRepository.save(superHero);
	}

	private SuperHero getById(Integer id) throws Exception {
		log.info("Get By Id...");
		Optional<SuperHero> superHeroOp = superHeroRepository.findByIdAndActive(id, true);

		if (!superHeroOp.isPresent()) {
			throw new NotFoundCustomException("Id: ".concat(id.toString()));
		}

		return superHeroOp.get();
	}
}