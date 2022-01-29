package com.jlopez.w2m.superhero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlopez.w2m.superhero.entity.SuperHero;

@Repository
public interface ISuperHeroRepository extends JpaRepository<SuperHero, Integer> {

	Optional<SuperHero> findById(Integer id);

	List<SuperHero> findByNameContainingIgnoreCase(String name);

	SuperHero findByName(String name);
}
