package com.jlopez.w2m.superhero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<List<SuperHeroResponse>> getAll() throws Exception {
		List<SuperHeroResponse> response = superHeroService.findAll();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}
