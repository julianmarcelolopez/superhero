package com.jlopez.w2m.superhero.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Actor not Found")
public class NotFoundCustomException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Not Found Exception";

	public NotFoundCustomException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
