package com.jlopez.w2m.superhero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlopez.w2m.superhero.entity.SuperHero;

@Repository
public interface ISuperHeroRepository extends JpaRepository<SuperHero, Integer> {

	List<SuperHero> findAllByActive(boolean active);
	
	Optional<SuperHero> findById(Integer id);
	
	Optional<SuperHero> findByIdAndActive(Integer id, boolean active);

	List<SuperHero> findByNameContainingIgnoreCase(String name);
	
	List<SuperHero> findByNameContainingIgnoreCaseAndActive(String name, boolean active);

	Optional<SuperHero> findByName(String name);
	
	Optional<SuperHero> findByNameAndActive(String name, boolean active);
}
