package com.jlopez.w2m.superhero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jlopez.w2m.superhero.aop.TrackTime;
import com.jlopez.w2m.superhero.dto.SuperHeroRequest;
import com.jlopez.w2m.superhero.dto.SuperHeroResponse;
import com.jlopez.w2m.superhero.service.ISuperHeroService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/superhero")
public class SuperHeroController {

	@Autowired
	private ISuperHeroService superHeroService;

	public void setSuperHeroService(ISuperHeroService superHeroService) {
		this.superHeroService = superHeroService;
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/")
	@ApiOperation(value = "Get All SuperHeros")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = List.class) })
	@TrackTime
	public ResponseEntity<List<SuperHeroResponse>> getAll() throws Exception {
		List<SuperHeroResponse> response = superHeroService.findAll();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Find SuperHero By Id")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = SuperHeroResponse.class) })
	@TrackTime
	public ResponseEntity<?> findById(@PathVariable Integer id) throws Exception {

		return ResponseEntity.ok(superHeroService.findById(id));
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping()
	@ApiOperation(value = "Find SuperHero By Name")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = SuperHeroResponse.class) })
	@TrackTime
	public ResponseEntity<?> findByName(@RequestParam String name) throws Exception {

		return ResponseEntity.ok(superHeroService.findByName(name));
	}

	@PostMapping()
	@PreAuthorize("hasRole('MANAGER')")
	@ApiResponses({ @ApiResponse(code = 200, message = "Created", response = SuperHeroResponse.class) })
	@TrackTime
	public ResponseEntity<SuperHeroResponse> saveHero(@RequestBody SuperHeroRequest superHeroRequest) throws Exception {
		SuperHeroResponse superHeroResponse = superHeroService.saveSuperHero(superHeroRequest);
		return new ResponseEntity<SuperHeroResponse>(superHeroResponse, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Update SuperHero")
	@ApiResponses({ @ApiResponse(code = 200, message = "Actualizado", response = SuperHeroResponse.class) })
	@TrackTime
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody SuperHeroRequest superHeroRequest)
			throws Exception {
		SuperHeroResponse superHeroResponse = superHeroService.updateSuperHero(id, superHeroRequest);
		return ResponseEntity.ok(superHeroResponse);
	}

	@PreAuthorize("hasRole('MANAGER')")
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete SuperHero")
	@ApiResponses({ @ApiResponse(code = 200, message = "Borrado") })
	@TrackTime
	public ResponseEntity<?> delete(@PathVariable Integer id) throws Exception {
		superHeroService.deleteSuperHeroById(id);
		return ResponseEntity.ok(HttpStatus.OK.value());

	}

}
