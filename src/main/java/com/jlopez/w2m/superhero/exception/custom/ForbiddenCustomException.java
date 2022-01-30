package com.jlopez.w2m.superhero.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Forbidden")
public class ForbiddenCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Forbidden Exception";

	public ForbiddenCustomException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
