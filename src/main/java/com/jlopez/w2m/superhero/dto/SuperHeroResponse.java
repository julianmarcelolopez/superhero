package com.jlopez.w2m.superhero.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SuperHeroResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String owner;
}