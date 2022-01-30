package com.jlopez.w2m.superhero.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnauthorizedCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Unauthorized Exception";

	public UnauthorizedCustomException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
